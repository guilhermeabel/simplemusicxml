package simple.musicxml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    Tag tag = new Tag();

    static BufferedWriter bw;

    String workTitle;
    String composer;
    String lyricist;

    int beats;
    int beatType;

    public void getAtributos(int lines, String array[][]) {
        workTitle = tag.getWorkTitle(lines, array);
        composer = tag.getComposer(lines, array);
        lyricist = tag.getLyricist(lines, array);
        beats = tag.getBeats(lines, array);
        beatType = tag.getBeatType(lines, array);
    }

    public void createFile(int lines, String array[][], String[][] outArray) {
        getAtributos(lines, array);
        try {
            bw = new BufferedWriter(new FileWriter(workTitle + ".html"));
            bw.write("<html>\n");

            bw.write("<head>\n");
            bw.write("<title>");
            bw.write(workTitle);
            bw.write("</title>\n");
            bw.write("</head>\n");

            bw.write("<body>\n");

            bw.write("<h1 align='center'>");
            bw.write(workTitle);
            bw.write("</h1>\n");

            bw.write("<p align='center'>");
            bw.write(composer);
            bw.write("</p>\n");

            bw.write("<p align='center'>");
            bw.write(lyricist);
            bw.write("</p>\n");

            bw.write("<p align='center'>\n");

            for (String item : outArray[0]) {
                bw.write(item);
                bw.write("&emsp;");
            }
            bw.write("</p>");
            bw.write("<p align='center'>");
            for (int coluna = 0; coluna < outArray[0].length; coluna++) {
                bw.write(outArray[1][coluna]);
                bw.write("&emsp;");
            }
            bw.write("</p>\n");
            bw.write("</body>\n");
            bw.write("</html>");
            bw.close();
        } catch (IOException e) {
        }
    }
}
