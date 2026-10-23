package com.mojang.authlib;

import java.util.UUID;

public class GameProfile {
    private final UUID id;
    private final String name;

    public GameProfile(UUID id, String name) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.name = name == null ? "" : name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
