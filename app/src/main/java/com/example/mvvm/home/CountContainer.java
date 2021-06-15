package com.example.mvvm.home;

public class CountContainer {
    private static int count;

    public static void updateCount(int count) {
        CountContainer.count = count;
    }

    public static int getCount() {
        return count;
    }
}
