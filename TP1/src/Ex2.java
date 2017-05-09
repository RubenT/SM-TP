import java.io.*;

/**
 * Created by RUBANECO on 05/05/2017.
 */
public class Ex2 {

    public static void joinFilesToCal() {

        File[] filesCal = new File[5];
        filesCal[0] = new File("alice29.txt");
        filesCal[1] = new File("cp.html");
        filesCal[2] = new File("fields.c");
        filesCal[3] = new File("geo");
        filesCal[4] = new File("kennedy.xls");
        File mergedFile = new File("Cal");
        mergeFiles(filesCal, mergedFile);
    }

    public static void joinFilesToCan(){
        File[] filesCAN = new File[5];
        filesCAN[0] = new File("asyoulik.txt");
        filesCAN[1] = new File("grammar.lsp");
        filesCAN[2] = new File("lcet10.txt");
        filesCAN[3] = new File("sum");
        filesCAN[4] = new File("xargs.1");
        File mergedFile = new File("Can");
        mergeFiles(filesCAN, mergedFile);
    }

    public static void joinFilesToSin(){
        File[] filesSIN = new File[5];
        filesSIN[0] = new File("dickens");
        filesSIN[1] = new File("mozilla");
        filesSIN[2] = new File("osdb");
        filesSIN[3] = new File("reymont");
        filesSIN[4] = new File("samba");
        File mergedFile = new File("Sin");
        mergeFiles(filesSIN, mergedFile);
    }

    private static void mergeFiles(File[] files, File mergedFile) {

        FileWriter fstream = null;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(mergedFile, true);
            out = new BufferedWriter(fstream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        for (File f : files) {
            System.out.println("merging: " + f.getName());
            FileInputStream fis;
            try {
                fis = new FileInputStream(f);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                String aLine;
                while ((aLine = in.readLine()) != null) {
                    out.write(aLine);
                    out.newLine();
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
