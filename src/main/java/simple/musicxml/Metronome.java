package simple.musicxml;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

public class Metronome implements MetaEventListener {

    private static Sequencer sequencer_metronome;
    int bpm;
    int vectorLength;
    String[] vector;

    public Sequence start(int bpm, int vectorLenght, String[] vetor) {

        this.vectorLength = vectorLenght;
        this.vector = vetor;

        try {
            this.bpm = bpm;
            openSequencer();
            Sequence seq_metronome = createSequence();
            seq_metronome = startSequence(seq_metronome);
            return seq_metronome;
        } catch (InvalidMidiDataException | MidiUnavailableException ex) {
            Logger.getLogger(Metronome.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void openSequencer() throws MidiUnavailableException {
        sequencer_metronome = MidiSystem.getSequencer();
        sequencer_metronome.open();
        sequencer_metronome.addMetaEventListener(this);
    }

    public Sequence createSequence() {
        try {
            Sequence seq_metronome = new Sequence(Sequence.PPQ, 1);
            Track metronome = seq_metronome.createTrack();

            //int x = vectorLength - 1;
            int i = 0;

            // while (vector[x] != null) {
            //     i++;
            //     x--;
            //     if (x == 0) {
            //         break;
            //     }
            // }

            // x = i;

            // for (i = 0; i < vectorLength; i++) {
            //     if (vector[i] != null) {
            //         addMetronomeEvent(metronome, 1);
            //     }
            // }

            for (i = 0; i < vector.length; i++) {
                addMetronomeEvent(metronome, 1);
            }

            return seq_metronome;

        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Metronome.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void addMetronomeEvent(Track metronome, long tick) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 0, 100, 40);
        MidiEvent event = new MidiEvent(message, tick);
        metronome.add(event);
    }

    private Sequence startSequence(Sequence seq_metronome) throws InvalidMidiDataException {
        sequencer_metronome.setSequence(seq_metronome);
        sequencer_metronome.setTempoInBPM(bpm);
        sequencer_metronome.start();
        return seq_metronome;
    }

    public void meta(MetaMessage message) {
        if (message.getType() != 47) {
            return;
        }
        doLoop();
    }

    private void doLoop() {
        if (sequencer_metronome == null || !sequencer_metronome.isOpen()) {
            return;
        }
        sequencer_metronome.setTickPosition(0);
        sequencer_metronome.setLoopStartPoint(0);
        sequencer_metronome.start();
        sequencer_metronome.setTempoInBPM(bpm);
        sequencer_metronome.setTempoFactor(1);
    }

    protected static void stopPlayback(Sequence seq_metronome) throws InvalidMidiDataException {
        sequencer_metronome.getSequence();
        sequencer_metronome.stop();
    }

}
