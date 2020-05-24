package com.qihui.concurrencypractice.buildingBlocks;

import lombok.SneakyThrows;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier  case
 */
public class CellularAutomate {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomate(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, mainBoard::commitNewValue);
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    private class Worker implements Runnable {
        private final Board board;
        public Worker(Board board) {
            this.board = board;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    return;
                }
            }
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        mainBoard.waitForConvergence();
    }

    /**
     *  This method takes so long time.
     */
    private int computeValue(int x, int y) throws InterruptedException {
        Thread.sleep(5000L);
        return 50;
    }
}



class Board {
    private int maxX;
    private int maxY;

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    boolean hasConverged() {
        return false;
    }

    void setNewValue(int x, int y, int computeValue) {

    }

    public void commitNewValue() {

    }

    public Board getSubBoard(int count, int i) {
        return new Board();
    }

    public void waitForConvergence() {

    }
}
