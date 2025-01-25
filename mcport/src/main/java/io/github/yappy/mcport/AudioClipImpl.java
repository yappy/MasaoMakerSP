package io.github.yappy.mcport;

import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class AudioClipImpl implements AudioClipMod{

    private static AtomicBoolean errorOnce = new AtomicBoolean(false);
    private Clip clip = null;

    public AudioClipImpl(byte[] data) {
        try {
            var bin = new ByteArrayInputStream(data);
            // In headless env (no sound device),
            // this method throws IllegalArgumentException (RuntimeException)
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
        } catch (Exception e) {
            if (!errorOnce.getAndSet(true)) {
                System.err.println("Failed to setup audio (this error is shown only once)");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void play() {
        System.out.println("AudioClipImpl.play");
        if (clip != null) {
            clip.start();
        }
    }

    @Override
    public void loop() {
        System.out.println("AudioClipImpl.loop");
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void stop() {
        System.out.println("AudioClipImpl.stop");
        if (clip != null) {
            clip.stop();
        }
    }

}
