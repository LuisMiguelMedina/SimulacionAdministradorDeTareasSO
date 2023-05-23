package org.example.Model;

public class Process {
    private final String name;
    private final int arrivalTime;
    private final int burstTime;
    private int remainingTime;
    private int executionTime = 0;

    public int getInitialBurstTime() {
        return initialBurstTime;
    }

    private int initialBurstTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.initialBurstTime = this.burstTime;

    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    public void increaseExecutionTime() {
        this.executionTime++;
    }
    public int getExecutionTime() {
        return this.executionTime;
    }
}