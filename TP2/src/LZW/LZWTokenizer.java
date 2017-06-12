package LZW;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class LZWTokenizer {
    private static Scanner io;
    private static int original = 0, compressed = 0;
    public static void execute() {
        //String filename = getFileName();
        String filename = "squares.raw";
        File f = new File(filename);
        original = (int)f.length();
        HashMap<String, Integer> dictionary = new HashMap<>();
        makeInitialDictionary(f, dictionary);
        LinkedList<Integer> output = new LinkedList<>();
        encodeLZW(f, dictionary, output);
        writeToFile(new File(filename + ".txt"), output);
    }

    private static void writeToFile(File file, LinkedList<Integer> output) {
        int outputLength = 0;
        try(FileWriter fr = new FileWriter(file)) {
            for (Integer i:
                 output) {
                String write = i + "\n";
                outputLength = write.toString().getBytes().length;
                fr.write(write);
                compressed += outputLength;
            }
            fr.write("Original = " + original + ", Compressed = " + compressed + ", Ratio = " + (1-((float)compressed/original))*100 + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encodeLZW(File f, HashMap<String, Integer> dictionary, LinkedList<Integer> output) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"Cp1252"))){
            int current, code = dictionary.size()+1, token = -1;
            while((current = bufferedReader.read()) != -1) {
                String key = "" + (char)current;
                while (dictionary.containsKey(key)) {
                    token = dictionary.get(key);
                    if ((current = bufferedReader.read()) != -1) {
                        key += current;
                    } else break;
                }
                dictionary.put(key, code);
                code++;
                output.addLast(token);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeInitialDictionary(File f, HashMap<String, Integer> dictionary) {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "Cp1252"))){
            int current, code = 1;
            while((current = bufferedReader.read()) != -1){
                String key = "" + (char)current;
                if(!dictionary.containsKey(key)) {
                    dictionary.put(key, code);
                    code++;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName() {
        System.out.print("Nome do ficheiro: ");
        io = new Scanner(System.in);
        String filename = io.nextLine();
        return filename;
    }


}
