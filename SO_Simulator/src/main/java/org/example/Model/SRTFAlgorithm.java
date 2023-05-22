package org.example.Model;

import org.example.View.GanttChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class SRTFAlgorithm {
    private final List<Process> Process;
    private double TEP;
    private double TTP;

    public SRTFAlgorithm() {
        Process = new ArrayList<>();
    }

    static void findWaitingTime(Process[] proc, int n, double[] wt, GanttChart chart) {
        double[] rt = new double[n];
        double contextSwitchTime = 0.2;
        int prevProcess = -1;
        int xor = 0;

        for (int i = 0; i < n; i++)
            rt[i] = proc[i].getBurstTime();

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((proc[j].getArrivalTime() <= t) && (rt[j] < minm) && rt[j] > 0) {
                    minm = (int)rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                t++;
                continue;
            }

            if (prevProcess != -1 && prevProcess != shortest) {
                wt[prevProcess] += contextSwitchTime;
                System.out.println("Cambio de contexto al proceso: " + proc[shortest].getName());
                if (xor <= 0) {
                    chart.addProcess(proc[prevProcess].getName(), rt[prevProcess]);
                    chart.addProcess(proc[shortest].getName(), rt[shortest]);
                    xor++;
                } else {
                    chart.addProcess(proc[shortest].getName(), rt[shortest]);
                }
            }
            rt[shortest]--;

            minm = (int)rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            if (rt[shortest] == 0) {
                complete++;
                check = false;
                finish_time = t + 1;
                wt[shortest] = finish_time - proc[shortest].getBurstTime() - proc[shortest].getArrivalTime();

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
            t++;
            prevProcess = shortest;
        }
    }

    // Method to calculate average time
    void findavgTime(Process[] proc, int n, GanttChart chart) {
        double[] wt = new double[n], tat = new double[n];
        double total_wt = 0, total_tat = 0;

        findWaitingTime(proc, n, wt, chart);
        findTurnAroundTime(proc, n, wt, tat);

        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            TTP += proc[i].getBurstTime();
            // Imprime el tiempo de espera para cada proceso
            System.out.println("Tiempo de espera para el proceso " + proc[i].getName() + ": " + wt[i]);
        }
        TEP = total_wt / n;
        double TTPQueConsumeTEP = getTEP()/getTTP();
        System.out.println(getTEP());
        System.out.println(getTTP());
        System.out.println(TTPQueConsumeTEP * 100);
    }

    // Method to calculate turn around time
    static void findTurnAroundTime(Process[] proc, int n, double[] wt, double[] tat) {
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].getBurstTime() + wt[i];
    }
    double getTEP() {
        return TEP;
    }

    double getTTP() {
        return TTP;
    }
    public void cargarProcesosDesdeCSV(GanttChart chart) {
        try (BufferedReader br = new BufferedReader(new FileReader("SO_Simulator/src/main/java/org/example/FileData/SRTFData.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = (datos[0].trim());
                int tiempoLlegada = Integer.parseInt(datos[1].trim());
                int duracion = Integer.parseInt(datos[2].trim());
                Process proceso = new Process(nombre, tiempoLlegada, duracion);
                Process.add(proceso);
            }
            findavgTime(this.Process.toArray(new Process[0]), this.Process.size(), chart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

