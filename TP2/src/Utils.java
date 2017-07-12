import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by RUBANECO on 13/05/2017.
 */
public class Utils {

    public static void printArrayToFile(Pair[] characters, String filename, int occurs) {
        try(FileWriter fr = new FileWriter(filename)) {
            for (int j = 0; j < characters.length; j++) {
                if (characters[j] != null)
                    fr.write(characters[j].toString());
            }
            fr.write(testAddUpOccurences(characters, occurs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Pair[] removeNullsAndSortArrayDecreasing(Pair[] pairs, int indexes) {
        Pair[] newPairs = new Pair[indexes];
        int j = 0;
        for (int i = 0; i < pairs.length; i++) {
            if(pairs[i] != null) {
                newPairs[j] = pairs[i];
                j++;
            }
        }
        for (int i = 1; i < newPairs.length; i++) {
            Pair aux = newPairs[i];
            if(aux!= null) {
                int k;
                for (k = i - 1; k >= 0 && aux.getOccur() > newPairs[k].getOccur(); k--)
                    newPairs[k + 1] = newPairs[k];
                newPairs[k + 1] = aux;
            }
        }
        return newPairs;
    }

    public static String testAddUpOccurences(Pair[] characters, int occurs) {
        int trueOccurs = 0;
        for (Pair p : characters) {
            if(p!=null)
                trueOccurs += p.getOccur();
        }
        return "Discrepancy between occurences = " + (occurs - trueOccurs);
    }
}
