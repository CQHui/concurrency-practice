package com.qihui.concurrencypractice.sharingObjects;

import java.util.Arrays;

/**
 * Unsafe to publish class. Internal mutable state escapes.
 */
public class UnSafeStates {
    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnSafeStates unSafeStates = new UnSafeStates();
        System.out.println(Arrays.toString(unSafeStates.getStates()));
        unSafeStates.getStates()[0] = "e";
        System.out.println(Arrays.toString(unSafeStates.getStates()));
    }
}
