package org.sda.core;

import java.util.ArrayList;
import java.util.List;
import org.sda.helpers.Peserta;

public class MergeSort {

    public static void sort(List<Peserta> list, int pilihan) {
        if (list == null || list.size() <= 1) {
            return;
        }

        int mid = list.size() / 2;
        List<Peserta> left = new ArrayList<>(list.subList(0, mid));
        List<Peserta> right = new ArrayList<>(list.subList(mid, list.size()));

        sort(left, pilihan);
        sort(right, pilihan);

        merge(list, left, right, pilihan);
    }

    private static void merge(
        List<Peserta> result,
        List<Peserta> left,
        List<Peserta> right,
        int pilihan
    ) {
        int i = 0,
            j = 0,
            k = 0;

        while (i < left.size() && j < right.size()) {
            Peserta pLeft = left.get(i);
            Peserta pRight = right.get(j);

            boolean kondisiDescending;
            if (pilihan == 1) {
                kondisiDescending = pLeft.getBobot1() >= pRight.getBobot1();
            } else {
                kondisiDescending = pLeft.getBobot2() >= pRight.getBobot2();
            }

            if (kondisiDescending) {
                result.set(k++, pLeft);
                i++;
            } else {
                result.set(k++, pRight);
                j++;
            }
        }

        while (i < left.size()) {
            result.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            result.set(k++, right.get(j++));
        }
    }
}
