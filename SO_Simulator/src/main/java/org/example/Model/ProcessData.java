package org.example.Model;

class ProcessData {
    private final String processName;
    private final double waitingTime;

    public ProcessData(String processName, double waitingTime) {
        this.processName = processName;
        this.waitingTime = waitingTime;
    }
    @Override
    public String toString() {
        return processName + ": " + waitingTime;
    }
}
