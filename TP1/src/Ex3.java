import java.io.*;

public class Ex3 {
    public static void symbolOccurences(String filename){
        String output = filename + "_SymbolOccurs.txt";
        System.out.println("Printing symbol occurences from '" + filename + "' to '" + output + "' ...");
        File f = new File(filename);
        Pair[] characters = new Pair[256];

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "Cp1252"))){
            int i, occurs = 0, indexes = 0;
            while ((i = br.read()) != -1){
                if(characters[i] == null) {
                    characters[i] = new Pair(i, 0);
                    indexes++;
                }
                characters[i].addOccur();
                occurs++;
            }

            characters = Utils.removeNullsAndSortArrayDecreasing(characters, indexes);
            Utils.printArrayToFile(characters, output, occurs);
            Utils.testAddUpOccurences(characters, occurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }

    public static void firstSymbolPerLineOccurences(String filename) {
        String output = filename + "_FirstSymbolOccurs.txt";
        System.out.println("Printing first symbol per line occurences from '" + filename + "' to '" + output + "' ...");
        File f = new File(filename);
        Pair[] characters = new Pair[256];

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "Cp1252"))){
            String s;
            int occurs = 0;
            int indexes = 0;
            while ((s = br.readLine()) != null){
                if(s.length() > 0) {
                    char c = s.charAt(0);
                    if (characters[c] == null) {
                        characters[c] = new Pair(c, 0);
                        indexes++;
                    }
                    characters[c].addOccur();
                    occurs++;
                }
            }

            characters = Utils.removeNullsAndSortArrayDecreasing(characters, indexes);
            Utils.printArrayToFile(characters, output, occurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }

    public static void symbolsAfterLetter(String filename, char letter) {
        String output = filename + "_symbolsAfterLetter" + letter + ".txt";
        System.out.println("Printing symbols after letter occurences from '" + filename + "' to '" + output + "' ...");
        File f = new File(filename);
        Pair[] characters = new Pair[256];

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "Cp1252"))){
            int c;
            int occurs = 0;
            int indexes = 0;
            while ((c = br.read()) != -1){
                if(c == letter) {
                    br.mark(1);
                    int next = br.read();
                    if (characters[next] == null) {
                        characters[next] = new Pair(next, 0);
                        indexes++;
                    }
                    characters[next].addOccur();
                    occurs++;
                    br.reset();
                }
            }

            characters = Utils.removeNullsAndSortArrayDecreasing(characters, indexes);
            Utils.printArrayToFile(characters, output, occurs);
            Utils.testAddUpOccurences(characters, occurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done!");
    }


}
