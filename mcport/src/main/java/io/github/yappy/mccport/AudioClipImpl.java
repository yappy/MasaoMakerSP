package io.github.yappy.mccport;

import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat.Encoding;

public class AudioClipImpl implements AudioClip {

    private Clip clip;

    public AudioClipImpl(URL url) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        /*System.out.println("Create audio clip: " + url);
        Clip clip = AudioSystem.getClip();
        AudioFormat format = clip.getFormat();
        // Clip supporting format PCM_SIGNED unknown sample rate, 16 bit, stereo, 4 bytes/frame, big-endian is supported.
        try (var ais = AudioSystem.getAudioInputStream(format, AudioSystem.getAudioInputStream(url))) {
            clip.open(ais);
            this.clip = clip;
        }*/
    }

    @Override
    public void play() {
        System.out.println("AudioClipImpl.play");
        //clip.start();
    }

    @Override
    public void loop() {
        System.out.println("AudioClipImpl.loop");
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void stop() {
        System.out.println("AudioClipImpl.stop");
        //clip.stop();
    }

}
