import csv

#Primer tiempo es en donde paro y el segundo cuanto duro en la memoria
def leer_archivo_csv(ruta_archivo):
    datos = []
    with open(ruta_archivo, "r") as archivo_csv:
        lector = csv.reader(archivo_csv)
        for fila in lector:
            proceso = fila[0]
            tiempo_salida = int(fila[1])
            tiempo_en_memoria = int(fila[2])
            proceso = [proceso,tiempo_salida,tiempo_en_memoria]
            datos.append(proceso)

    return datos

lista = leer_archivo_csv("Data.csv")

for i in lista:
    print(i)
