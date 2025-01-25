package io.github.yappy.mcport;

import org.junit.Test;

import io.github.yappy.mcport.McMod.McVersion;

import static org.junit.Assert.*;

public class McModTest {

    @Test
    public void staticInitialize() {
        assertNotNull(McMod.class);
    }

    @Test
    public void getHelp() {
        for (var ver : McVersion.values()) {
            String help = McMod.getHelp(ver);
            assertNotNull(help);
            assertNotEquals("", help);
        }
    }

    @Test
    public void getParam() {
        for (var ver : McVersion.values()) {
            var names = McMod.getParamNames(ver);

            String defName = McMod.getDefParamName(ver);
            assertTrue(names.contains(defName));

            for (var name : names) {
                var paramList = McMod.getParam(ver, name);
                assertFalse(paramList.isEmpty());
                for (var param : paramList) {
                    assertNotNull(param.name());
                    assertNotEquals("", param.name());
                    assertNotNull(param.value());
                    assertNotNull(param.comment());
                }
            }
        }
    }

    @Test
    public void getImage() {
        for (var ver : McVersion.values()) {
            var map = McMod.getDefImages(ver);
            assertFalse(map.isEmpty());
        }
    }

    @Test
    public void getSound() {
        for (var ver : McVersion.values()) {
            var map = McMod.getDefSounds(ver);
            assertNotNull(map);
        }
    }

}
