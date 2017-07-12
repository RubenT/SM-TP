package SMVideoConverterEditor;

import java.io.IOException;
import java.util.Scanner;

public class Commands {

    private static Scanner io;
    public static void convertVideo() {
        io = new Scanner(System.in);
        System.out.print("Input video:\n" +
                ">");
        String input = io.nextLine();
        System.out.print("Output video:\n" +
                ">");
        String output = io.nextLine();

        try {
            new ProcessBuilder("ffmpeg.exe", "-i", input, output).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractFrames() {

        io = new Scanner(System.in);
        System.out.print("Input video:\n" +
                ">");
        String input = io.nextLine();
        System.out.print("Frames per second:\n" +
                ">");
        String fps = io.nextLine();
        System.out.print("Start time (hh:mm:ss):\n" +
                ">");
        String start = io.nextLine();
        System.out.print("End time (hh:mm:ss):\n" +
                ">");
        String end = io.nextLine();
        try {
            new ProcessBuilder("ffmpeg.exe", "-i", input, "-r", fps, "-ss", start, "-to", end, "output%03d.jpg").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractAudio() {
        io = new Scanner(System.in);
        System.out.print("Input video:\n" +
                ">");
        String input = io.nextLine();
        System.out.print("Quality (higher value = lower quality):\n" +
                ">");
        String quality = io.nextLine();
        System.out.print("Start time (hh:mm:ss):\n" +
                ">");
        String start = io.nextLine();
        System.out.print("End time (hh:mm:ss):\n" +
                ">");
        String end = io.nextLine();
        try {
            new ProcessBuilder("ffmpeg.exe", "-i", input, "-ss", start, "-to", end, "-aq", quality, "-f", "mp3", "output.mp3").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
