package simple.musicxml;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

public class Player implements MetaEventListener {

    private static Sequencer sequencer;
    int bpm;
    int vectorLength;
    String[] vector;

    public Sequence start(int bpm, int vectorLenght, String[] vetor) {

        this.vectorLength = vectorLenght;
        this.vector = vetor;

        try {
            this.bpm = bpm;
            openSequencer();
            Sequence seq = createSequence();
            seq = startSequence(seq);
            return seq;
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void openSequencer() throws MidiUnavailableException {
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.addMetaEventListener(this);
    }

    public Sequence createSequence() {
        try {
            Sequence seq = new Sequence(Sequence.PPQ, 1);
            Track track = seq.createTrack();
            int x = vectorLength - 1;
            int i = 0;
            while ("\u25CB".equals(vector[x])) {
                i++;
                x--;
                if (x == 0) {
                    break;
                }
            }
            x = i;
            for (i = 0; i < vector.length; i++) {
                if (vector[i].equals("\u25CF")) {
                    addNoteEvent(track, (i + x) + 1);
                }
                /*
                 * else if (vector[i].equals("\u25CB")) { addNoteEvent(track, i - 1); }
                 */
            }
            return seq;
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void addNoteEvent(Track track, long tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 9, 37, 100);
        MidiEvent event = new MidiEvent(message, tick);
        track.add(event);
    }

    private Sequence startSequence(Sequence seq) throws InvalidMidiDataException {
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(bpm);
        sequencer.start();
        return seq;
    }

    public void meta(MetaMessage message) {
        if (message.getType() != 47) { // 47 é o final da track
            return;
        }
        doLoop();
    }

    private void doLoop() {
        if (sequencer == null || !sequencer.isOpen()) {
            return;
        }
        sequencer.setTickPosition(0);
        sequencer.start();
        sequencer.setTempoInBPM(bpm);
    }

    protected static void stopPlayback(Sequence seq) throws InvalidMidiDataException {
        sequencer.getSequence();
        sequencer.stop();
    }

}
