package ru.temon137.labyrintharium.World.GameObjects;

public interface IAudioComponent {
    void playChangePositionSound();

    void playSpawnSound();

    void playDespawnSound();

    void playGettingDamageSound();

    void playUseSound();
}
