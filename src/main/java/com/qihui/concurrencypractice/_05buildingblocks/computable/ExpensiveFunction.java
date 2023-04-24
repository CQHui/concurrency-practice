package com.qihui.concurrencypractice._05buildingblocks.computable;

/**
 * @author chenqihui
 * @date 2020/5/29
 */
public class ExpensiveFunction implements Computable<String, Integer>{
    @Override
    public Integer compute(String arg) throws InterruptedException {
        //takes long time
        Thread.sleep(10 * 1000L);
        return Integer.valueOf(arg);
    }
}
