
import java.io.*;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by RUBANECO on 19/06/2017.
 */
public class Golomb {

    public static void execute(String filename, int m) {
        int pixels[][] = readImage(filename);
        Scanner io = new Scanner(System.in);
        showPreditors();
        int command = io.nextInt();
        int data [] = choosePreditor(command, pixels);
        golombDecomposition(filename, m, data);
    }

    private static int[] choosePreditor(int command, int pixels[][]) {
        switch(command){
            case 1: return convertToArray(pixels);
            case 2: return preditorJPEG1(pixels);
            case 3: return preditorJPEG2(pixels);
            case 4: return preditorJPEG3(pixels);
            case 5: return preditorJPEG4(pixels);
            case 6: return preditorJPEG5(pixels);
            case 7: return preditorJPEG6(pixels);
            case 8: return preditorJPEG7(pixels);
            case 9: return preditorJPEG4(pixels);
            default: return null;
        }
    }

    private static void showPreditors() {
        System.out.print("Preditor list:\n" +
                "1. JPEG: no prediction\n" +
                "2. JPEG: A\n" +
                "3. JPEG: B\n" +
                "4. JPEG: C\n" +
                "5. JPEG: A+B-C\n" +
                "6. JPEG: A+((B-C)/2)\n" +
                "7. JPEG: B+((A-C)/2)\n" +
                "8. JPEG: (A+B)/2\n" +
                "9. JPEG-LS: A+B-C\n" +
                ">");
    }

    private static int[][] readImage(String filename) {
        File f = new File(filename);
        try(FileInputStream fs = new FileInputStream(f)){
            int pixels[][]=new int[256][256];
            for (int x=0;x<256;x++)
            {
                for(int y=0;y<256;y++)
                {
                    pixels[x][y]= fs.read();
                }
            }
            return pixels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int [] convertToArray(int [][] pixels)
    {
        int c = 0;
        int [] converted = new int[256*256];
        for(int j = 0; j < 256; ++j){
            for(int k = 0; k < 256; ++k){
                converted[c] = pixels[j][k];
                ++c;
            }
        }
        return converted;
    }

    private static int[] preditorJPEG1(int[][] pixels)
    {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int A = pixels[x-1][y];
                newPixels[x][y] = pixels[x][y] - A;
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG2(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int B = pixels[x][y-1];
                newPixels[x][y] = pixels[x][y] - B;
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG3(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int C = pixels[x-1][y-1];
                newPixels[x][y] = pixels[x][y] - C;
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG4(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int A = pixels[x-1][y];
                int B = pixels[x][y-1];
                int C = pixels[x-1][y-1];
                newPixels[x][y] = pixels[x][y] - (A + B - C);
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG5(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int A = pixels[x-1][y];
                int B = pixels[x][y-1];
                int C = pixels[x-1][y-1];
                newPixels[x][y] = pixels[x][y] - (A + ((B - C)/2));
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG6(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int A = pixels[x-1][y];
                int B = pixels[x][y-1];
                int C = pixels[x-1][y-1];
                newPixels[x][y] = pixels[x][y] - (B + ((A - C)/2));
            }
        }
        return convertToArray(newPixels);
    }

    private static int[] preditorJPEG7(int[][] pixels) {
        int [][] newPixels = new int [256][256];
        newPixels = fillMargins(newPixels, pixels);
        for (int x=1;x<255;x++) {
            for (int y = 1; y < 255; y++) {
                int A = pixels[x-1][y];
                int B = pixels[x][y-1];
                newPixels[x][y] = pixels[x][y] - ((A + B)/2);
            }
        }
        return convertToArray(newPixels);
    }

    private static int[][] fillMargins(int[][] newPixels, int [][] oldPixels) {
        for(int x = 0; x < 256; x+=255) {
            for (int y = 0; y < 256; ++y) {
                newPixels[x][y] = oldPixels[x][y];
            }
        }
        for(int x = 0; x < 256; x+=255) {
            for (int y = 0; y < 256; ++y) {
                newPixels[y][x] = oldPixels[y][x];
            }
        }
        return newPixels;
    }

    public static void golombDecomposition(String filename, int m, int[] data){
        String output = filename + "_Golomb_m=" + m + ".txt";
        System.out.println("Printing symbol occurences from '" + filename + "' to '" + output + "' ...");
        File f = new File(filename);
        HashMap<Integer, Pair> characters = new HashMap();
        int indexes = 0;
        for (int b : data){
            if(!characters.containsKey(b)) {
                characters.put(b, new Pair(b, 0));
                indexes++;
            }
            characters.get(b).addOccur();
        }
        Pair[] chars = characters.values().toArray(new Pair[characters.size()]);
        chars = Utils.removeNullsAndSortArrayDecreasing(chars, indexes);
        printArrayToFileGolomb(chars, output, (int)f.length(), m);
        Utils.testAddUpOccurences(chars, (int)f.length());
        System.out.println("Done!");
    }

    private static void printArrayToFileGolomb(Pair[] characters, String filename, int occurs, int m) {

        int firstValues = (int) ((Math.pow(2, Math.ceil(Math.log(m)/ Math.log(2))) - m));
        int firstBits = (int) Math.floor(Math.log(m)/ Math.log(2));
        int n = 0;
        int mq = m;
        StringBuilder result = new StringBuilder("");
        String unary = "";
        int outputLength = 0;
        try(FileWriter fr = new FileWriter(filename)) {
            for (int j = 0; j < characters.length; j++) {
                int firstR = n%m;
                int lastR = n%m +(int)((Math.pow(2, Math.ceil(Math.log(m)/ Math.log(2))) - m));
                if(mq == 0) {
                    firstValues = (int) ((Math.pow(2, Math.ceil(Math.log(m) / Math.log(2))) - m));
                    mq = m;
                }if (characters[j] != null) {
                    unary += getUnaryCode(n / m);
                    if (firstValues != 0) {
                        result.append(addNumberOfBits(Integer.toBinaryString(firstR), firstBits));
                        firstValues--;
                    } else {
                        result.append(Integer.toBinaryString(lastR));
                    }
                    n++;
                    result.insert(0, unary).append("\n");
                    outputLength += result.toString().getBytes().length;
                    fr.write(result.toString());
                }
                mq--;
                unary = "";
                result = new StringBuilder("");
            }
            fr.write("Taxa de Compressão: " + ((float)outputLength/occurs) * 100 + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUnaryCode(int q){
        String res = "";
        for(int i = 0; i < q; i++){
            res += "1";
        }
        res += "0";
        return res;
    }

    private static StringBuilder addNumberOfBits(String res, int numOfBits){
        StringBuilder addedBits = new StringBuilder("");
        while(numOfBits > res.length()){
            addedBits.insert(0, "0");
            --numOfBits;
        }
        return addedBits.append(res);
    }
}
