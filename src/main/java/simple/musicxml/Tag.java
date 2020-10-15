package simple.musicxml;

public class Tag {

    Parser parser = new Parser();

    Note note = new Note();

    public String getWorkTitle(int lines, String array[][]) {
        String workTitle = "";
        String startTagName = "<work-title>";
        String finalTagName = "</work-title>";
        for (int i = 0; i < lines; i++) {
            String tagContent = getTagContent(array, i, startTagName, finalTagName);
            if (!tagContent.equals("")) {
                workTitle = tagContent;
            }
        }
        return workTitle;
    }

    public String getComposer(int lines, String array[][]) {
        String composer = "";
        String startTagName = "<creator type=\"composer\">";
        String finalTagName = "</creator>";
        for (int i = 0; i < lines; i++) {
            String tagContent = getTagContent(array, i, startTagName, finalTagName);
            if (!tagContent.equals("")) {
                composer = tagContent;
            }
        }
        return composer;
    }

    public String getLyricist(int lines, String array[][]) {
        String lyricist = "";
        String startTagName = "<creator type=\"lyricist\">";
        String finalTagName = "</creator>";
        for (int i = 0; i < lines; i++) {
            String tagContent = getTagContent(array, i, startTagName, finalTagName);
            if (!tagContent.equals("")) {
                lyricist = tagContent;
            }
        }
        return lyricist;
    }

    public int getBeats(int lines, String array[][]) {
        int beats = 0;
        String startTagName = "<beats>";
        String finalTagName = "</beats>";
        for (int i = 0; i < lines; i++) {
            String tagContent = getTagContent(array, i, startTagName, finalTagName);
            if (!tagContent.equals("")) {
                beats = Integer.parseInt(tagContent);
            }
        }
        return beats;
    }

    public int getBeatType(int lines, String array[][]) {
        int beatType = 0;
        String startTagName = "<beat-type>";
        String finalTagName = "</beat-type>";
        for (int i = 0; i < lines; i++) {
            String tagContent = getTagContent(array, i, startTagName, finalTagName);
            if (!tagContent.equals("")) {
                beatType = Integer.parseInt(tagContent);
            }
        }
        return beatType;
    }

    public String getTagContent(String matriz[][], int line, String startTagName, String finalTagName) {

        String tagContent = "";

        if (matriz[line][0].contains(startTagName)) {
            int initialPosition = matriz[line][0].indexOf(startTagName);
            int initialTagContent = initialPosition + startTagName.length();
            int finalPosition = matriz[line][0].indexOf(finalTagName);
            tagContent = matriz[line][0].substring(initialTagContent, finalPosition);
        }
        return tagContent;
    }

    public String getRest(String matriz[][], int line, String tag) {

        String tagContent = "";

        if (matriz[line][0].contains(tag)) {
            tagContent = "" + line;
        }
        return tagContent;
    }
}
