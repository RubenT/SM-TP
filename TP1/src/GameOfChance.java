import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameOfChance {
    private static Scanner io = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        getCommands();
    }

    private static void getCommands() {
        Boolean running = true;
        while(running) {
            System.out.println("\nComandos:\n" +
                    "1 - Registar apostador manualmente\n" +
                    "2 - Registar apostador automaticamente\n" +
                    "3 - Registar N apostadores automaticamente\n" +
                    "4 - Acrescentar aposta manualmente\n" +
                    "5 - Acrescentar N apostas automaticamente\n" +
                    "6 - Gerar chave\n" +
                    "7 - Verificar prémios\n" +
                    "8 - Fechar aplicação\n" +
                    "Inserir número do comando desejado:");
            int input = io.nextInt();
            switch(input){
                case 1: registerPlayerManual(); break;
                case 2: registerPlayerAutomatic(); break;
                case 3: registerNPlayers(); break;
                case 4: addBetManually(); break;
                case 5: addNBets(); break;
                case 6: generateResult(); break;
                case 7: getPrizes(); break;
                case 8: running = false; break;
                default:
                    System.out.println("Comando inválido."); break;
            }
        }
    }

    private static void addNBets() {
        System.out.println("Quantas apostas pretende gerar?");
        int n = io.nextInt();
        System.out.println("Em que sorteio deseja apostar? (nº do sorteio)");
        int sorteio = io.nextInt();
        for (int i = 0; i < n; i++){
            addBetAutomatically(sorteio);
        }
    }

    private static void registerNPlayers() {
        System.out.println("Quantos jogadores pretende gerar?");
        int n = io.nextInt();
        for (int i = 0; i < n; i++){
            registerPlayerAutomatic();
        }
    }

    private static void registerPlayerManual() {
        // Input do número de cidadão
            System.out.println("Inserir informação do apostador:\n" +
                    "Número de Cidadão:");
            Boolean valid = false;
            String id = "";
            io.nextLine();
            while(!valid) {
                id = io.nextLine();
                while (!id.matches("[0-9]+") || id.length() != 8) {
                    System.out.println("O número de cidadão apenas pode conter números e necessita de ter exatamente 8 números.");
                    id = io.nextLine();
                }
                try {
                    valid = true;
                    if (containsPlayer(id, "apostadores.txt")) {
                        System.out.println("Este número de cidadão já está registado.");
                        valid = false;
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        // Input do nome
        System.out.println("Nome(s) Próprio(s) e Apelido(s):");
        String nome = io.nextLine();
        String[] nomeToArray = nome.split(" ");
        while(nomeToArray.length < 2 || nomeToArray.length > 4) {
            System.out.println("Apenas são aceites nomes da seguinte forma:\n" +
                    "Um nome próprio e um apelido, dois nomes próprios e um apelido, dois nomes próprios e dois apelidos");
            nome = io.nextLine();
            nomeToArray = nome.split(" ");
        }
        // Input da localidade
        System.out.println("Localidade:");
        String local = io.nextLine();
        // A data preenchida é a data actual
        String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        // Criação da string a adicionar ao ficheiro através das strings anteriores
        String line = id + "|" + nome + "|" + local + "|" + data + "\n";
        // Escrita no ficheiro
        try {
            appendToFile("apostadores.txt", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerPlayerAutomatic() {
        String id, nome = "";
        try {
                id = "";
                for (int i = 0; i < 8; i++) {
                    id += random.nextInt(10);
                }

            nome = getRandomName();
            String local = getRandomLocal();
            String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String line = id + "|" + nome + "|" + local + "|" + data + "\n";
            appendToFile("apostadores.txt", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Jogador " + nome + " adicionado.");
    }

    private static void addBetManually() {
        System.out.println("Insira o seu número de cidadão");
        Boolean valid = false;
        String id = "";
        io.nextLine();
        while(!valid) {
            id = io.nextLine();
            while (!id.matches("[0-9]+") || id.length() != 8) {
                System.out.println("O número de cidadão apenas pode conter números e necessita de ter exatamente 8 números.");
                id = io.nextLine();
            }
            try {
                valid = true;
                if (!containsPlayer(id, "apostadores.txt")) {
                    System.out.println("Este número de cidadão não está registado.");
                    valid = false;
                    break;
                }

                if (containsPlayer(id, "apostas.txt")) {
                    System.out.println("Este cidadão já registou uma aposta.");
                    valid = false;
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Em que sorteio deseja apostar? (nº do sorteio)");
        int sorteio = io.nextInt();
        System.out.println("Deseja fazer a aposta manualmente? (y/n)");
        char yn = 0;
        while(yn != 'y' && yn != 'n') {
            yn = io.next().charAt(0);
        }
        valid = false;
        int[] numbers = new int[3];
        while(!valid) {
            if (yn == 'y') {
                System.out.println("Escolha 3 números não repetidos de 1 a 15 (Ex: 9 3 14)");
                numbers[0] = io.nextInt(); numbers[1] = io.nextInt(); numbers[2] = io.nextInt();
                valid = validateNumbers(numbers);
            }
            if(yn == 'n') {
                numbers = generateRandomBet();
                System.out.println("Os seus números são: " + numbers[0] + "-" + numbers[1] + "-" + numbers[2]);
                valid = true;
            }
        }
        String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String line = id + "|" + numbers[0] + "-" + numbers[1] + "-" + numbers[2] + "|" + data + "|" + sorteio + "\n";
        try {
            appendToFile("apostas.txt", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addBetAutomatically(int sorteio) {
        try {
            String id = "";
            for (int i = 0; i < 8; i++) {
                id += random.nextInt(10);
            }
            int[] numbers = generateRandomBet();
            String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String line = id + "|" + numbers[0] + "-" + numbers[1] + "-" + numbers[2] + "|" + data + "|" + sorteio + "\n";
            appendToFile("apostas.txt", line);
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getId(int i) throws IOException {
        String currentId;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("apostadores.txt"))))) {
                String current;
                for (int j = 0; j < i-1; j++) {
                    br.readLine();
                }
                return br.readLine().substring(0, 7);
            }
    }

    private static int[] generateRandomBet() {
        int[] ret = new int[3];
        ret[0] = random.nextInt(14)+1;
        ret[1] = ret[0];
        ret[2] = ret[0];
        while(ret[1] == ret[0])
            ret[1] = random.nextInt(14)+1;
        while(ret[2] == ret[0] || ret[2] == ret[1])
            ret[2] = random.nextInt(14)+1;
        return ret;
    }

    private static Boolean validateNumbers(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] < 1 || numbers[i] > 15) return false;
        }
        if(numbers[1] == numbers[0]) return false;
        if(numbers[2] == numbers [1] || numbers[2] == numbers[0]) return false;
        return true;
    }

    private static void generateResult() {
        System.out.println("Deseja a chave de qual sorteio? (nº do sorteio)");
        int sorteio = io.nextInt();
        String result = "";
        try {
            if ((result = findLotteryResults(sorteio)) == null){
                System.out.println("Este sorteio ainda não tem resultado. Deseja gerá-lo? (y/n)");
                char yn = 0;
                while(yn != 'y' && yn != 'n') {
                    yn = io.next().charAt(0);
                }
                if (yn == 'y') {
                    int[] numbers = generateRandomBet();
                    System.out.println("O resultado do " + sorteio + "º sorteio é " + numbers[0] + "-" + numbers[1] + "-" + numbers[2]);
                    try {
                        appendToFile("ResultadosDoSorteio.txt", sorteio + "|" + numbers[0] + "-" + numbers[1] + "-" + numbers[2] + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else System.out.println("O resultado do " + sorteio + "º sorteio é " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findLotteryResults(int sorteio) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("ResultadosDoSorteio.txt"))))) {
            String current;
            int currentLottery = -1;
            String[] currentArray;
            while ((current = br.readLine()) != null) {
                currentArray = current.split("\\|");
                currentLottery = parseInt(currentArray[0].substring(0, currentArray[0].length()));
                if (currentLottery == sorteio) {
                    return currentArray[1].substring(0, currentArray[1].length());
                }
            }
        }
        return null;
    }

    private static void getPrizes() {
        System.out.println("Deseja verificar os prémios de que sorteio? (nº do sorteio)");
        int sorteio = io.nextInt();
        LinkedList<String> firstPlaces = new LinkedList<>();
        LinkedList<String> secondPlaces = new LinkedList<>();
        LinkedList<String> thirdPlaces = new LinkedList<>();
        try {
            LinkedList<String> entries = getLotteryEntries(sorteio);
            String[] result = findLotteryResults(sorteio).split("-");
            for (String s: entries) {
                int matches = 0;
                String[] numbers = s.split("\\|")[1].split("-");
                for (int i = 0; i < result.length; i++) {
                    if(result[i].equals(numbers[i])) matches++;
                }
                if(matches==1)
                    thirdPlaces.add(s);
                else if(matches==2)
                    secondPlaces.add(s);
                else if(matches==3)
                    firstPlaces.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("First places:");
        for (String s:firstPlaces) System.out.println(s);
        System.out.println("Second places:");
        for (String s:secondPlaces) System.out.println(s);
        System.out.println("Third places:");
        for (String s:thirdPlaces) System.out.println(s);
    }

    private static LinkedList<String> getLotteryEntries(int sorteio) throws IOException {
        LinkedList<String> ls = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("apostas.txt"))))) {
            String current;
            while ((current = br.readLine()) != null){
                if(parseInt(current.split("\\|")[3]) == sorteio) ls.add(current);
            }
        }
        return ls;
    }

    private static boolean containsPlayer(String id, String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))))) {
            String current;
            while ((current = br.readLine()) != null)
                if (current.contains(id)) {
                    return true;
                }
        }
        return false;
    }

    private static void appendToFile(String filename, String line) throws IOException {
        try(BufferedWriter output = new BufferedWriter(new FileWriter(filename, true))) {
            output.append(line);
        }
    }

    private static String getRandomName() throws IOException {
        int namesize = random.nextInt(3);
        String name = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Nomes.txt")), "Cp1252"))) {
            String current;
            int count = countLines("Nomes.txt");
            int nameIndex1 = random.nextInt(count);
            int nameIndex2 = random.nextInt(count);
            String name1 = "";
            String name2= "";
            for (int i = 0; i <= nameIndex1 || i <= nameIndex2; i++) {
                current = br.readLine();
                if(i == nameIndex1) name1 = current;
                if(i == nameIndex2) name2 = current;
            }
            name += namesize > 0 ? name1 + " " + name2 + " " : name1 + " ";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Apelidos.txt")), "Cp1252"))) {
            String current;
            int count = countLines("Apelidos.txt");
            int nameIndex1 = random.nextInt(count);
            int nameIndex2 = random.nextInt(count);
            String name1 = "";
            String name2= "";
            for (int i = 0; i <= nameIndex1 || i <= nameIndex2; i++) {
                current = br.readLine();
                if(i == nameIndex1) name1 = current;
                if(i == nameIndex2) name2 = current;
            }
            name += namesize == 2 ? name1 + " " + name2 : name1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    private static String getRandomLocal() {
        String current = null;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Localidades.txt")), "Cp1252"))){
            int size = countLines("Localidades.txt");
            int count = random.nextInt(size)-2;
            br.readLine(); br.readLine();
            for (int i = 0; i < count; i++) {
                current = br.readLine();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
}
