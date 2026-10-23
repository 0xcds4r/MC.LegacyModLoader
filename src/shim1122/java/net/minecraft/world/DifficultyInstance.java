package net.minecraft.world;

public class DifficultyInstance {
    private final EnumDifficulty difficulty;

    public DifficultyInstance(World world, long worldTime, long chunkInhabitedTime, float moonPhaseFactor) {
        this.difficulty = EnumDifficulty.NORMAL;
    }

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isHarderThan(float difficulty) {
        return this.difficulty.getId() > difficulty;
    }

    public float getAdditionalDifficulty() {
        return 0.0F;
    }

    public enum EnumDifficulty {
        PEACEFUL(0),
        EASY(1),
        NORMAL(2),
        HARD(3);

        private final int id;

        EnumDifficulty(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
