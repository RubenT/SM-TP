import java.util.Scanner;

public class App {
    private static boolean running;
    public static void main(String[] args) {
        init();
    }

    private static void init() {
        running = true;
        Scanner io = new Scanner(System.in);
        while(running){
            showCommands();
            int command = io.nextInt();
            run(command);
        }
    }

    private static void run(int command) {
        switch(command){
            case 1: LZWTokenizer.execute(); break;
            case 2: SMVideoConverterEditor.execute(); break;
            case 3: running = false;
            default: break;
        }
    }

    private static void showCommands() {
        System.out.print("Command list:\n" +
                "1. LZWTokenizer\n" +
                "2. SMVideoConverterEditor\n" +
                "3. Exit\n" +
                ">");
    }
}
