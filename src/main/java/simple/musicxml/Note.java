package simple.musicxml;

public class Note {

    int vectorLenght = 0;

    public int getVectorLenght(int lines, String array[][]) {
        Tag tag = new Tag();
        for (int i = 0; i < lines; i++) {
            String tagContent = tag.getTagContent(array, i, "<type>", "</type>");
            if (!tagContent.equals("")) {
                vectorLenght += countNote(tagContent);
            }
        }
        return vectorLenght;
    }

    // Conta a dimensão do vetor de acordo com o tipo da nota
    public int countNote(String type) {
        if (type.equals("quarter")) {
            vectorLenght += 2;
            return 2;
        } else if (type.equals("eighth")) {
            vectorLenght += 1;
            return 1;
        }
        return 0;
    }
}
