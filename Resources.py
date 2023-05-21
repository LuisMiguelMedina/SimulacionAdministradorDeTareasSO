import tkinter as tk
from tkinter import ttk
from Srtf import srtf
from LectorCSV import leer_archivo_csv
colores_pastel = [
    '#FFB6C1',  # Rosado claro
    '#FFC0CB',  # Rosa pastel
    '#FFDAB9',  # Durazno claro
    '#FFDEAD',  # Albaricoque
    '#FFE4B5',  # Amarillo albaricoque
    '#FFE4C4',  # Melocotón claro
    '#FFEBCD',  # Blanco floral
    '#FFEFD5',  # Blanco antiguo
    '#FFF0F5',  # Lavanda rosada
    '#FFF5EE',  # Marfil floral
    '#FFF8DC',  # Amarillo maíz
    '#FFFACD',  # Amarillo limón claro
    '#FFFAF0',  # Blanco fantasma
    '#FFFAFA',  # Blanco nieve
    '#FFFFE0',  # Amarillo claro
    '#FFFFF0'   # Marfil
]
#Textos Vanilla
text_presentacion = "Cálculo de tiempos. El cambio de contexto se realiza 0.2 milisegundos"
text_tiempo_espera = "Tiempo de espera por cada proceso :"
text_espera_promedio_todos = "Tiempo de espera promedio de todos los proceses (TEP) :"
text_tiempo_total_procesamiento = "Tiempo total de procesamiento de todos los procesos (TTP):"
text_porcentaje_TTP = "Porcentaje del TTP que consume TEP:"


ruta_del_archivo = "Data.csv"
processes = leer_archivo_csv(ruta_del_archivo)
tep, ttp, total_processing_time, percentage_tep = srtf(processes)


lista = list(tep)
salida = ""
for i in range(len(lista) -1):
    salida += "P" + str(i) + ": " + str(lista[i]) + " "

#Textos Finalizados
sal_text_presentacion = "Cálculo de tiempos. El cambio de contexto se realiza 0.2 milisegundos"
sal_text_tiempo_espera = "Tiempo de espera por cada proceso : " + str(salida)
sal_text_espera_promedio_todos = "Tiempo de espera promedio de todos los proceses (TEP) : " + str(ttp)
sal_text_tiempo_total_procesamiento = "Tiempo total de procesamiento de todos los procesos (TTP):" + str(total_processing_time)
sal_text_porcentaje_TTP = "Porcentaje del TTP que consume TEP:" + str(percentage_tep)

def text_global(canvas: tk.Canvas, finalizado:bool):
    if(finalizado == False):
        canvas.create_text(50,100, text=text_presentacion,anchor="nw")
        canvas.create_text(50,120, text=text_tiempo_espera, anchor="nw")
        canvas.create_text(50,140, text=text_espera_promedio_todos, anchor="nw")
        canvas.create_text(50,160, text=text_tiempo_total_procesamiento, anchor="nw")
        canvas.create_text(50,180, text=text_porcentaje_TTP, anchor="nw")
    else:
        canvas.create_text(50,100, text=sal_text_presentacion,anchor="nw")
        canvas.create_text(50,120, text=sal_text_tiempo_espera, anchor="nw")
        canvas.create_text(50,140, text=sal_text_espera_promedio_todos, anchor="nw")
        canvas.create_text(50,160, text=sal_text_tiempo_total_procesamiento, anchor="nw")
        canvas.create_text(50,180, text=sal_text_porcentaje_TTP, anchor="nw")

def create_table(canvas:tk.Canvas):
    # Dimensiones de la tabla
    tabla_width = 200
    tabla_height = 200
    tabla_x = canvas.winfo_width() - tabla_width - 10
    tabla_y = 50

    # Dibuja el rectángulo de la tabla
    tabla_rect = canvas.create_rectangle(tabla_x, tabla_y, tabla_x + tabla_width, tabla_y + tabla_height, outline="black")

    # Lee los datos desde un archivo CSV
    datos_csv = leer_archivo_csv('Data.csv')

    # Agrega el contenido de la tabla
    for i, fila in enumerate(datos_csv):
        for j, valor in enumerate(fila):
            etiqueta = tk.Label(canvas, text=valor, bg="white")
            etiqueta.place(x=tabla_x + 10 + (j * 80), y=tabla_y + 10 + (i * 30))
