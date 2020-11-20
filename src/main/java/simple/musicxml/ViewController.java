package simple.musicxml;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Abel
 */
public class ViewController {
    int vectorLength = 0;
    String vector[];

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private AnchorPane frame;

    @FXML
    private TextField filePath;

    @FXML
    private Button close;

    @FXML
    private Button fileButton;

    @FXML
    private Button minimize;

    @FXML
    private TextArea sheetPanel;

    @FXML
    private Button stop;

    @FXML
    private Button play;

    @FXML
    private Button reset;

    @FXML
    private Slider sliderBPM;
    private Sequence seq;
    private Sequence seq_metronome;

    @FXML
    void initialize() {
        play.setDisable(true);
        stop.setDisable(true);
        reset.setDisable(true);
        // funcTeste();
    }

    @FXML
    void closeFrame(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    void minimizeFrame(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void selectFile(ActionEvent event) {
        FileChooser file = new FileChooser();
        file.getExtensionFilters().add(new ExtensionFilter("Arquivos MusicXML", "*.musicxml"));
        File f = file.showOpenDialog(null);

        if (f != null) {
            filePath.setText(f.getAbsolutePath());
            play.setDisable(false);
            reset.setDisable(false);
            fileButton.setDisable(true);
            config();
        }
    }

    private void config() {

        MusicXML musicXML = new MusicXML();
        Parser parser = new Parser();
        Note note = new Note();
        Output output = new Output();
        // BUSCA O CAMINHO DO ARQUIVO QUE SERA TRADUZIDO NO FORMATO .musicXML
        String fileName = filePath.getText();
        // BUSCA O NUMERO DE LINHAS DO ARQUIVO
        int lines = musicXML.getLines(fileName);
        // CRIA UM ARRAY COM O CONTEUDO DO ARQUIVO
        String[][] array = musicXML.readFile(fileName, lines);
        // BUSCA O TAMANHO DO VETOR
        int vectorLenght = note.getVectorLenght(lines, array);
        vector = parser.vectorCreate(lines, array, vectorLenght);
        parser.setVectorLenght(vectorLenght);
        // int tam = parser.getVectorLenght();
        String outArray[][] = new String[2][(vectorLenght / 2) + 1];
        outArray = parser.setSimpleArray(vector, vectorLenght);
        // exibe na GUI o conteúdo do vetor

        for (int i = 0, pos0 = 0, pos1 = 0; i < 4; i++) {
            if (i > 0) {
                sheetPanel.appendText("\n"); // evita quebra de linha antes da primeira linha
                sheetPanel.appendText("    "); // espaço vazio da partitura do ó
            }

            for (int x = 0; pos0 < outArray[0].length; pos0++) {
                sheetPanel.appendText(outArray[0][pos0] + " ");
                if (x == 4) { // x = 4 define o measure
                    x = 0;
                    pos0++;
                    break;
                }
                x++;
            }

            sheetPanel.appendText("\n");
            for (int x = 1; pos1 < outArray[1].length; pos1++) {
                sheetPanel.appendText(outArray[1][pos1] + " ");
                if (x == 4) { // x = 4 define o measure
                    x = 0;
                    pos1++;
                    break;
                }
                x++;
            }
        }
        // output.getAtributos(lines, array);
        output.createFile(lines, array, outArray);
    }

    @FXML
    void playMusic(ActionEvent event) {
        int bpm = (int) sliderBPM.getValue();
        bpm *= 2;
        new Player().start(bpm, vector.length, vector);
        new Metronome().start(bpm, vector.length, vector);
        play.setDisable(true);
        stop.setDisable(false);
        reset.setDisable(false);
    }

    @FXML
    void resetMusic(ActionEvent event) throws InvalidMidiDataException {
        if (seq != null) {
            Player.stopPlayback(this.seq); // Chamada do Player
            Metronome.stopPlayback(this.seq_metronome); // Chamada do Metronomo
        }
        resetFields();
        play.setDisable(true);
        stop.setDisable(true);
        reset.setDisable(true);
        fileButton.setDisable(false);
    }

    @FXML
    void stopMusic(ActionEvent event) throws InvalidMidiDataException {
        Player.stopPlayback(this.seq);
        Metronome.stopPlayback(this.seq_metronome);
        play.setDisable(false);
    }

    private void resetFields() {
        sheetPanel.selectAll();
        sheetPanel.replaceSelection("");
        filePath.setText("");
    }

}
