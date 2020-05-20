package io.github.vampirestudios.raa_core.api;

public interface RAAAddon {

    String[] shouldLoadAfter();

    String getId();

    void onInitialize();

}
