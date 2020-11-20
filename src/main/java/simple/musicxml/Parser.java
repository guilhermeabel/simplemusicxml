package simple.musicxml;

public class Parser {

    Note note = new Note();

    String[] vector;
    int vectorLenght;

    public String[] vectorCreate(int lines, String array[][], int vectorLenght) {
        vector = new String[vectorLenght];
        Tag tag = new Tag();
        int index = 0;
        int rests = 0;
        for (int i = 0; i < lines; i++) {
            String tagContent = tag.getTagContent(array, i, "<type>", "</type>");
            String rest = tag.getRest(array, i, "<rest/>");
            int noteType;
            if (!rest.equals("")) {
                rests++;
            }
            if (!tagContent.equals("")) {
                noteType = note.countNote(tagContent);
                index = setVector(index, noteType, rests);
                rests = 0;
            }
        }
        return vector;
    }

    // Seta o vetor com "cículo" e "ponto" de acordo com o tipo da nota e pausas
    public int setVector(int index, int noteType, int rests) {
        if (noteType == 1 && rests == 0) {
            vector[index] = "\u25CF";
            index++;
            return index;
        } else if (noteType == 1 && rests == 1) {
            vector[index] = "\u25CB";
            index++;
            return index;
        } else if (noteType == 2 && rests == 1) {
            vector[index] = "\u25CB";
            index++;
            vector[index] = "\u25CB";
            index++;
            return index;
        } else {
            vector[index] = "\u25CF";
            index++;
            vector[index] = "\u25CB";
            index++;
            return index;
        }
    }

    public String[][] setSimpleArray(String[] vector, int vectorLength) {

        int column = (vectorLength / 2) + 1;
        int index = 0;

        String outArray[][] = new String[2][column];

        for (int i = 0, j = 0; j < column; j++) {

            if (i == 0 && j == 0) {
                outArray[i][j] = "   "; // primeiro espaço
                i++;
            }
            if (j == (column - 1) && i == 1) {
                outArray[i][j] = " ";
                j++;
            }
            if (i == 0) {
                outArray[i][j] = vector[index];
                index++;
                i++;
                j--;
            } else if (index != vectorLength) {
                outArray[i][j] = vector[index];
                index++;
                i--;
            }
        }
        return outArray;
    }

    // Seta a variável global "vector" com a variável que recebeu como parâmetro
    public void setVector(String vector[]) {
        this.vector = vector;
    }

    public void setVectorLenght(int vectorLenght) {
        this.vectorLenght = vectorLenght;
    }
}
