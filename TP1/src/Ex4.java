import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;

public class Ex4 {

    public static void golombDecomposition(String filename, int m){
        String output = filename + "_Golomb_m=" + m + ".txt";
        System.out.println("Printing symbol occurences from '" + filename + "' to '" + output + "' ...");
        File f = new File(filename);
        Pair[] characters = new Pair[256];

        byte [] data = new byte[(int)f.length()];
        try(FileInputStream fs = new FileInputStream(f)){
            int indexes = 0;
            fs.read(data);
            for (byte b : data){
                if(characters[b + 128] == null) {
                    characters[b + 128] = new Pair(b, 0);
                    indexes++;
                }
                characters[b + 128].addOccur();
            }

            characters = Utils.removeNullsAndSortArrayDecreasing(characters, indexes);
            printArrayToFileGolomb(characters, output, (int)f.length(), m);
            Utils.testAddUpOccurences(characters, (int)f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            fr.write("Taxa de Compressão: " + (1 - ((float)outputLength/occurs)) * 100 + "%");
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
