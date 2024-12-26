package io.github.yappy.mccport;

import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClipImpl implements AudioClip {

    private Clip clip;

    public AudioClipImpl(byte[] data) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        var bin = new ByteArrayInputStream(data);
        Clip clip = AudioSystem.getClip();
        AudioFormat format = clip.getFormat();
        // Clip supporting format PCM_SIGNED unknown sample rate, 16 bit, stereo, 4
        // bytes/frame, big-endian is supported.
        try (var ais = AudioSystem.getAudioInputStream(format, AudioSystem.getAudioInputStream(bin))) {
            clip.open(ais);
            this.clip = clip;
        }
        clip.addLineListener(ev -> {
            if (ev.getType() == LineEvent.Type.STOP) {
                Clip c = (Clip) ev.getSource();
                c.stop();
                c.setFramePosition(0);
            }
        });
    }

    @Override
    public void play() {
        System.out.println("AudioClipImpl.play");
        clip.start();
    }

    @Override
    public void loop() {
        System.out.println("AudioClipImpl.loop");
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stop() {
        System.out.println("AudioClipImpl.stop");
        clip.stop();
    }

}
