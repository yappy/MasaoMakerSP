package io.github.yappy.mcutil;

import org.junit.Ignore;
import org.junit.Test;
//import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioTest {

    @Test
    //@Ignore("This test will fail if the system is headless or WSL")
    public void init() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        AudioFormat format = clip.getFormat();
        // Clip supporting format PCM_SIGNED unknown sample rate, 16 bit,
        //stereo, 4 bytes/frame, big-endian is supported.
        try (var ais = AudioSystem.getAudioInputStream(format,
                AudioSystem.getAudioInputStream(new File("../sound/pcm/clear.au")))) {
            clip.open(ais);
        }
    }
}
