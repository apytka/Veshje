package com.agatap.veshje.model.utils;

import java.util.Comparator;
import java.util.List;

public class Utils {

    public static <E> void sortCompare(List<E> list, Comparator<E> comparator) {
        int maxIndex;
        E tmp;
        for (int j = 0; j < list.size(); j++) {
            maxIndex = j;
            for (int i = j + 1; i < list.size(); i++) {
                if (comparator.compare(list.get(maxIndex), list.get(i)) > 0) {
                    maxIndex = i;
                }
            }
            tmp = list.get(maxIndex);
            list.set(maxIndex, list.get(j));
            list.set(j, tmp);
        }
    }
}
