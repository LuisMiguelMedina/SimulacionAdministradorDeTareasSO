# SRTF con esquema Preentive
# Cada cambio de contexto se realiza en un tiempo de 0.2 milisegundos
from LectorCSV import leer_archivo_csv


def srtf(processes):
    n = len(processes)
    burst_time = [0] * n
    remaining_time = [0] * n
    arrival_time = [0] * n
    turnaround_time = [0] * n
    waiting_time = [0] * n
    completed = 0
    current_time = 0
    context_switch_time = 0.2

    for i in range(n):
        burst_time[i] = processes[i][2]
        remaining_time[i] = burst_time[i]
        arrival_time[i] = processes[i][1]

    while completed != n:
        shortest = -1
        shortest_index = -1

        for i in range(n):
            if remaining_time[i] > 0 and arrival_time[i] <= current_time:
                if shortest == -1 or remaining_time[i] < remaining_time[shortest]:
                    shortest = i
                    shortest_index = i

        if shortest == -1:
            current_time += 1
            continue

        remaining_time[shortest] -= 1

        if remaining_time[shortest] == 0:
            completed += 1
            end_time = current_time + 1
            turnaround_time[shortest] = end_time - arrival_time[shortest]
            waiting_time[shortest] = turnaround_time[shortest] - burst_time[shortest]
            current_time += context_switch_time
        else:
            current_time += 1

    total_waiting_time = sum(waiting_time)
    average_waiting_time = total_waiting_time / n
    total_processing_time = sum(burst_time)
    percentage_tep = (total_waiting_time / total_processing_time) * 100

    return waiting_time, average_waiting_time, total_processing_time, percentage_tep


# Ejemplo de uso
ruta_del_archivo = "../CSV_Files/Data.csv"
processes = leer_archivo_csv(ruta_del_archivo)
tep, ttp, total_processing_time, percentage_tep = srtf(processes)

print("Tiempo de espera de cada proceso:")
for i in range(len(processes)):
    print(f"{processes[i][0]}: {tep[i]}")

print(f"\nTiempo de espera promedio de todos los procesos (TEP): {ttp}")
print(f"Tiempo total de procesamiento de todos los procesos (TTP): {total_processing_time}")
print(f"Porcentaje del TTP que consume el TEP: {percentage_tep}%")
