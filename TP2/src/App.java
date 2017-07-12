import SMVideoConverterEditor.VideoApp;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class App {
    private static boolean running;
    public static void main(String[] args) {
        run();
    }

    private static void run() {
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
            case 1: Golomb.execute("squares.raw", 3);break;
            case 2: LZWTokenizer.execute(); break;
            case 3: VideoApp.run(); break;
            case 4: running = false;
            default: break;
        }
    }

    private static void showCommands() {
        System.out.print("Command list:\n" +
                "1. Golomb\n" +
                "2. LZWTokenizer\n" +
                "3. SMVideoConverterEditor\n" +
                "4. Exit\n" +
                ">");
    }
}
