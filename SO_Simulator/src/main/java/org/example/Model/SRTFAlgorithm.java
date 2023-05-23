package org.example.Model;

import org.example.View.GanttChart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SRTFAlgorithm {
    private final List<ProcessTime> waitingTimes = new ArrayList<>();
    private final List<Process> processes;
    private double TEP;
    private double TTP;
    private double TTPQueConsumeTEP;

    public SRTFAlgorithm() {
        processes = new ArrayList<>();
    }

    public double getTEP() {
        return TEP;
    }

    public double getTTP() {
        return TTP;
    }

    public double getTTPQueConsumeTEP() {
        return TTPQueConsumeTEP;
    }

    static void findWaitingTime(Process[] proc, int n, double[] wt, GanttChart chart) {
        double[] rt = new double[n];
        double contextSwitchTime = 0.2;
        int prevProcess = -1;
        boolean isFirstProcess = true;
        boolean isLastProcess = false;
        int first = 0;

        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++)
            rt[i] = proc[i].getBurstTime();

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Process until all processes gets completed
        while (complete != n) {
            isLastProcess = complete == n-1;

            // Find process with minimum remaining time at the time of completion of previous process
            for (int j = 0; j < n; j++) {
                if ((proc[j].getArrivalTime() <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = (int) rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            // If there was a context switch and it's not the first or last process
            if (prevProcess != -1 && prevProcess != shortest && !isFirstProcess && !isLastProcess) {
                wt[prevProcess] += contextSwitchTime;
                proc[prevProcess].setRemainingTime((int) rt[prevProcess]);
                chart.addProcess(proc[prevProcess].getName(), proc[prevProcess].getInitialBurstTime(), proc[prevProcess].getExecutionTime());
                chart.addProcess(proc[shortest].getName(), proc[shortest].getInitialBurstTime(), proc[shortest].getExecutionTime());
            }

            // Decrease remaining time by one
            rt[shortest]--;

            // Increase execution time by one
            proc[shortest].increaseExecutionTime();

            // Update minimum
            minm = (int) rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (rt[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current process
                finish_time = t + 1;

                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].getBurstTime() - proc[shortest].getArrivalTime();

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
            // Increment time
            t++;
            prevProcess = shortest;
            isFirstProcess = false;
        }
    }

    static void findTurnAroundTime(Process[] proc, int n, double[] wt, double[] tat) {
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].getBurstTime() + wt[i];
    }

    void findavgTime(Process[] proc, int n, GanttChart chart) {
        double[] wt = new double[n];
        double[] tat = new double[n];
        double total_wt = 0, total_tat = 0;

        findWaitingTime(proc, n, wt, chart);
        findTurnAroundTime(proc, n, wt, tat);

        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            TTP += proc[i].getBurstTime();
            waitingTimes.add(new ProcessTime(proc[i].getName(), wt[i]));
        }

        this.TEP = total_wt / n;
        this.TTP = total_tat;
        this.TTPQueConsumeTEP = getTEP() / getTTP() * 100;
    }

    public String getWaitingTimes() {
        StringBuilder sb = new StringBuilder();
        for (ProcessTime pt : waitingTimes) {
            sb.append(pt.toString()).append(", ");
        }
        return sb.toString();
    }

    public void cargarProcesosDesdeCSV(GanttChart chart) {
        try (BufferedReader br = new BufferedReader(new FileReader("SO_Simulator/src/main/java/org/example/FileData/SRTFData.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0].trim();
                int tiempoLlegada = Integer.parseInt(datos[1].trim());
                int duracion = Integer.parseInt(datos[2].trim());
                Process proceso = new Process(nombre, tiempoLlegada, duracion);
                proceso.setRemainingTime(duracion); // Actualizar remainingTime
                processes.add(proceso);
            }
            findavgTime(processes.toArray(new Process[0]), processes.size(), chart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}