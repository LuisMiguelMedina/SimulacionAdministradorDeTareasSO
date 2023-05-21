import csv


def leer_archivo_csv(ruta_archivo):
    datos = []
    with open(ruta_archivo, "r") as archivo_csv:
        lector = csv.reader(archivo_csv)
        for fila in lector:
            proceso = fila[0]
            tiempo_llegada = int(fila[1])
            tiempo_rafaga = int(fila[2])
            proceso = [proceso,tiempo_llegada,tiempo_rafaga]
            datos.append(proceso)

    return datos
