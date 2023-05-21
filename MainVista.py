from random import randrange
from random import choice
from LectorMemoria import leer_archivo_csv
import tkinter as tk
from Resources import *

# Lista de colores pastel
colores_pastel = colores_pastel
global lista_procesos_memoria 
root = tk.Tk()
canvas = tk.Canvas(root, width=1000, height=600)
canvas.pack()




text_global(canvas,False)

#Función para generar tabla
def create_rectangle_with_text(canvas = canvas):
    create_table(canvas)
    #Se cambia 
    lista_procesos_memoria = leer_archivo_csv("Data.csv")
    i = -1
    current = lista_procesos_memoria[i]
    
    

    alt = 350
    # Coordenadas del rectángulo
    x1 = 10
    y1 = 50 + alt
    x2 = current[2] * 10
    y2 = 100 + alt
    
    def add_rectangle(): 
        
        texto_boton()
        nonlocal x2, y2,i,lista_procesos_memoria, canvas  # Acceder a las variables externas
        create_table(canvas)
        if(i >= len(lista_procesos_memoria)-1):
            i = -1
            clear_rectangles()
        i += 1
        current = lista_procesos_memoria[i]
            
        
        # Coordenadas del nuevo rectángulo
        new_x1 = x2 + 0
        new_y1 = y1
        new_x2 = x2  + current[2] * 20
        new_y2 = y2

        if (y2 - y1 != 50): 
            new_y1 = y2 - 50
        
        # Si no hay suficiente espacio en el ancho, se genera el rectángulo debajo
        if (new_x2 >= canvas.winfo_width() - 10):
            new_x1 = 10
            new_y1 = y2 + 30
            new_x2 = current[2] * 20
            new_y2 = new_y1 + 50
        
        #Reiniciar si se va muy para abajo
        if (new_y2 >= canvas.winfo_height()):
            new_x1 = 10
            new_y1 = 50 + alt
            new_x2 = current[2] * 20
            new_y2 = 100 + alt
            clear_rectangles()
            
        
        
        # Dibuja el nuevo rectángulo con un color pastel aleatorio
        new_rectangle = canvas.create_rectangle(new_x1, new_y1, new_x2, new_y2, fill=choice(colores_pastel))
        
        # Texto dentro del nuevo rectángulo
        new_text = current[0]
        new_text_x = (new_x1 + new_x2) / 2
        new_text_y = (new_y1 + new_y2) / 2
        canvas.create_text(new_text_x, new_text_y, text=new_text)
        
        # Dato en la esquina inferior derecha (fuera del rectángulo)
        dato = current[1]
        dato_x = new_x2 
        dato_y = new_y2 
        canvas.create_text(dato_x, dato_y, text=dato, anchor='nw')
        
        # Actualiza las coordenadas del rectángulo original
        x2 = new_x2
        y2 = new_y2
        
    
    def clear_rectangles(canvas=canvas):
        nonlocal x2, y2,x1,y1,i
        x1 = 10
        y1 = 50 + alt
        x2 = 10
        y2 = 100 + alt
        canvas.delete('all')
        text_global(canvas,False)
        #Función de generar tabla

    
    def texto_boton(canvas=canvas):
        nonlocal i
        if(i < len(lista_procesos_memoria)-2):
            texto = "Paso" + str(i+1)
            add_button.config(text=texto)
        else:
            add_button.config(text="Finalizado")
            text_global(canvas,True)
            
    
    
    texto_inicial = "Paso" + str(i)
    
    # Botón para agregar un rectángulo adicional
    add_button = tk.Button(root, text=texto_inicial, command=add_rectangle)
    add_button.pack()
    
    # Botón para limpiar los rectángulos
    clear_button = tk.Button(root, text="Limpiar", command=clear_rectangles)
    clear_button.pack()
    root.mainloop()
    
    

create_rectangle_with_text()