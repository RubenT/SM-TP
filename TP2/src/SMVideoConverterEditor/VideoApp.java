package SMVideoConverterEditor;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class VideoApp {
    private static boolean running;
    public static void run() {
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
            case 1: Commands.convertVideo(); break;
            case 2: Commands.extractFrames(); break;
            case 3: Commands.extractAudio(); break;
            case 4: running = false; break;
            default: break;
        }
    }

    private static void showCommands() {
        System.out.print("Command list:\n" +
                "1. Convert video\n" +
                "2. Extract frames\n" +
                "3. Extract audio\n" +
                "4. Exit\n" +
                ">");
    }
}
