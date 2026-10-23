package net.minecraft.util;

import java.util.ArrayList;
import java.util.Collection;

public class NonNullList<E> extends ArrayList<E> {
    public NonNullList() {
        super();
    }

    public NonNullList(int size) {
        super(size);
    }

    public NonNullList(Collection<? extends E> values) {
        super(values);
    }

    public static <E> NonNullList<E> create() {
        return new NonNullList<>();
    }

    public static <E> NonNullList<E> create(int size) {
        return new NonNullList<>(size);
    }

    public static <E> NonNullList<E> withSize(int size, E fill) {
        NonNullList<E> list = new NonNullList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(fill);
        }
        return list;
    }

    public static <E> NonNullList<E> from(E... elements) {
        NonNullList<E> list = new NonNullList<>();
        java.util.Collections.addAll(list, elements);
        return list;
    }
}
