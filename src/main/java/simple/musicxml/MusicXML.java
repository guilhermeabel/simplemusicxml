package simple.musicxml;

import java.io.BufferedReader;
import java.io.FileReader;

public class MusicXML {
    // Busca o número de linhas do arquivo musicxml
    public int getLines(String fileName) {
        String path = fileName;
        BufferedReader br;
        int lines = 0;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((br.readLine()) != null) {
                lines++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lines;
    }

    public String[][] readFile(String fileName, int lines) {
        String matriz[][] = new String[lines][1];
        BufferedReader br;
        String line = "";
        int i = 0;
        try {
            br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                matriz[i][0] = line;
                i++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return matriz;
    }
}
