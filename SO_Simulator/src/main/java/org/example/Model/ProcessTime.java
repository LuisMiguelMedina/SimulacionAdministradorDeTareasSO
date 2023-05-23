package org.example.Model;

public class ProcessTime {
    private final String processName;
    private final double waitingTime;

    public ProcessTime(String processName, double waitingTime) {
        this.processName = processName;
        this.waitingTime = waitingTime;
    }

    public String getProcessName() {
        return processName;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String toString() {
        return processName + ": " + waitingTime;
    }
}