package io.github.vampirestudios.raa_core.api.client;

public interface RAAAddonClient {

    void onClientInitialize();

    String getId();

    String[] shouldLoadAfter();

}
