package net.minecraft.util;

import java.util.List;
import java.util.Random;

public class WeightedRandom {
    public static <T extends Item> T getRandomItem(Random random, List<T> list) {
        return getRandomItem(random, list, 0);
    }

    public static <T extends Item> T getRandomItem(Random random, List<T> list, int weight) {
        if (list.isEmpty()) {
            return null;
        }
        int total = 0;
        for (T item : list) {
            total += item.itemWeight;
        }
        int target = random.nextInt(total + weight);
        for (T item : list) {
            target -= item.itemWeight;
            if (target < 0) {
                return item;
            }
        }
        return list.get(list.size() - 1);
    }

    public static class Item {
        protected final int itemWeight;

        public Item(int itemWeight) {
            this.itemWeight = itemWeight;
        }
    }
}
