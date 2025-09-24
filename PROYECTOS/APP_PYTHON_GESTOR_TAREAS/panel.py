import ttkbootstrap as tb
from ttkbootstrap.constants import *
from database import get_connection
import tkinter as tk
from tkinter import simpledialog, Toplevel, messagebox




class Dashboard(tb.Window):
    def __init__(self, usuario_id):
        super().__init__(themename="darkly")
        self.title("Gestor de Tareas - Tableros")
        self.state('zoomed')
        self.usuario_id = usuario_id
        self.tablero_actual_id = None


        top_frame = tb.Frame(self, padding=10)
        top_frame.pack(side=TOP, fill=X)


        self.entry_busqueda = tb.Entry(top_frame, width=30)
        self.entry_busqueda.pack(side=LEFT, padx=10)


        tb.Button(top_frame, text="🔍 Buscar tablero", bootstyle="primary-outline", command=self.buscar_tablero).pack(side=LEFT)
        tb.Button(top_frame, text="🔄 Mostrar todos", bootstyle="info-outline", command=self.cargar_tableros).pack(side=LEFT, padx=5)


        tb.Label(top_frame, text="🗂️ TABLEROS", font=("Arial", 26, "bold")).pack(side=LEFT, padx=10)


        tb.Button(top_frame, text="➕ Añadir tablero", bootstyle="success", command=self.agregar_tablero).pack(side=RIGHT)
        tb.Button(top_frame, text="🗑️ Eliminar tablero", bootstyle="danger", command=self.eliminar_tablero).pack(side=RIGHT, padx=5)


        self.tablero_frame = tb.Frame(self, padding=10)
        self.tablero_frame.pack(fill=BOTH, expand=YES)


        self.tableros = []
        self.cargar_tableros()


    def agregar_tablero(self):
        nombre = simpledialog.askstring("Nuevo Tablero", "Nombre del tablero:")
        if nombre:
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("INSERT INTO tableros (nombre, usuario_id) VALUES (?, ?)", (nombre, self.usuario_id))
            conn.commit()
            conn.close()
            self.cargar_tableros()


    def eliminar_tablero(self):
        if not self.tablero_actual_id:
            return
        if messagebox.askyesno("Confirmar", "¿Eliminar este tablero y todo su contenido?"):
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("DELETE FROM tableros WHERE id = ?", (self.tablero_actual_id,))
            conn.commit()
            conn.close()
            self.tablero_actual_id = None
            self.cargar_tableros()


    def cargar_tableros(self):
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT id, nombre FROM tableros WHERE usuario_id = ?", (self.usuario_id,))
        self.tableros = cur.fetchall()
        conn.close()


        for widget in self.tablero_frame.winfo_children():
            widget.destroy()


        if not self.tableros:
            tb.Label(self.tablero_frame, text="No hay tableros creados", font=("Arial", 16), bootstyle="warning").pack(pady=20)
            return


        tb.Label(self.tablero_frame, text="Selecciona un tablero para abrirlo:", font=("Arial", 18, "bold")).pack(pady=10)


        for tablero_id, nombre in self.tableros:
            tb.Button(self.tablero_frame, text=nombre, bootstyle="info outline", width=30,
                      command=lambda tid=tablero_id, n=nombre: self.mostrar_tablero(tid, n)).pack(pady=5)


    def mostrar_tablero(self, tablero_id, nombre):
        self.tablero_actual_id = tablero_id
        for widget in self.tablero_frame.winfo_children():
            widget.destroy()


        # Frame con título y botones de tablero
        top_tablero_frame = tb.Frame(self.tablero_frame)
        top_tablero_frame.pack(fill=X, pady=5)


         # Nombre tablero en verde oscuro
        label = tb.Label(top_tablero_frame, text=f"📋 {nombre}", font=("Arial", 20, "bold"), foreground="#006400")  # dark green
        label.pack(side=LEFT)


        tb.Button(top_tablero_frame, text="✏️ Modificar Tablero", bootstyle="warning",
                  command=lambda: self.editar_nombre_tablero(tablero_id)).pack(side=LEFT, padx=5)
        tb.Button(top_tablero_frame, text="🗑️ Eliminar Tablero", bootstyle="danger",
                  command=self.eliminar_tablero).pack(side=LEFT, padx=5)


        tb.Button(top_tablero_frame, text="📅 Filtrar tareas por fecha", bootstyle="info",
                  command=self.filtrar_tareas_por_fecha).pack(side=RIGHT)


        listas_frame = tb.Frame(self.tablero_frame)
        listas_frame.pack(fill=BOTH, expand=YES)


        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT id, nombre FROM listas WHERE tablero_id = ?", (tablero_id,))
        listas = cur.fetchall()
        conn.close()


        for lista in listas:
            self.crear_columna_lista(listas_frame, lista[0], lista[1])


        tb.Button(self.tablero_frame, text="➕ Añadir lista", bootstyle="success",
                  command=lambda: self.agregar_lista(tablero_id)).pack(pady=10)


    def crear_columna_lista(self, parent, lista_id, nombre):
        frame = tb.Labelframe(parent, text=nombre, padding=10, width=220, bootstyle="success")
        frame.pack(side=LEFT, padx=10, fill=Y)

        # Título personalizado con color rojo y tamaño 18
        tb.Label(frame, font=("Arial", 14, "bold"), foreground="#FF0000").pack(pady=(0, 5))

        # Botones modificar y eliminar lista
        btns_frame = tb.Frame(frame)
        btns_frame.pack(fill=X, pady=5)
        tb.Button(btns_frame, text="✏️ Modificar Lista", bootstyle="warning",
                  command=lambda: self.editar_nombre_lista(lista_id)).pack(side=LEFT, padx=2)
        tb.Button(btns_frame, text="🗑️ Eliminar Lista", bootstyle="danger",
                  command=lambda: self.eliminar_lista(lista_id)).pack(side=RIGHT, padx=2)


        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT id, titulo, descripcion, fecha, estado FROM tareas WHERE lista_id = ?", (lista_id,))
        tareas = cur.fetchall()
        conn.close()


        for tarea in tareas:
            estado = "✅" if tarea[4] == "completada" else "⬜"
             # Título tarea en azul oscuro + resto texto normal:
            text = f"{tarea[1]}\n{tarea[2]}\n📅 {tarea[3]}\n{estado}"
            
             # Usamos un frame para separar título y resto y personalizar:
            tarea_frame = tb.Frame(frame, padding=5)
            tarea_frame.pack(pady=5, fill=X)

            # Label título azul oscuro
            titulo_label = tb.Label(tarea_frame, text=tarea[1], font=("Arial", 12, "bold"), foreground="#00008B", anchor=W, justify=LEFT)
            titulo_label.pack(fill=X)

            # Label descripción y fecha y estado en color neutro
            detalle_label = tb.Label(tarea_frame, text=f"{tarea[2]}\n📅 {tarea[3]}\n{estado}", anchor=W, justify=LEFT, wraplength=180)
            detalle_label.pack(fill=X)

            tarea_btns = tb.Frame(frame)
            tarea_btns.pack(fill=X, pady=2)


            tb.Button(tarea_btns, text="✔️ Cambiar Estado", bootstyle="secondary",
                      command=lambda tid=tarea[0]: self.cambiar_estado_tarea(tid)).pack(side=LEFT, padx=2)
            tb.Button(tarea_btns, text="✏️ Modificar Tarea", bootstyle="warning",
                      command=lambda tid=tarea[0]: self.editar_tarea(tid)).pack(side=LEFT, padx=2)


        tb.Button(frame, text="➕ Añadir tarea", bootstyle="warning", command=lambda: self.agregar_tarea(lista_id)).pack(pady=5)


    def agregar_lista(self, tablero_id):
        nombre = simpledialog.askstring("Nueva Lista", "Nombre de la lista:")
        if nombre:
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("INSERT INTO listas (nombre, tablero_id) VALUES (?, ?)", (nombre, tablero_id))
            conn.commit()
            conn.close()
            self.mostrar_tablero(tablero_id, self.obtener_nombre_tablero(tablero_id))


    def eliminar_lista(self, lista_id):
        if messagebox.askyesno("Eliminar Lista", "¿Eliminar esta lista y todas sus tareas?"):
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("DELETE FROM listas WHERE id = ?", (lista_id,))
            conn.commit()
            conn.close()
            self.recargar_tablero()


    def agregar_tarea(self, lista_id):
        def guardar():
            titulo = entry_titulo.get().strip()
            descripcion = entry_desc.get().strip()
            fecha = entry_fecha.get().strip()
            if titulo:
                conn = get_connection()
                cur = conn.cursor()
                cur.execute("INSERT INTO tareas (titulo, descripcion, fecha, estado, lista_id) VALUES (?, ?, ?, ?, ?)",
                            (titulo, descripcion, fecha, "pendiente", lista_id))
                conn.commit()
                conn.close()
                top.destroy()
                self.recargar_tablero()
            else:
                messagebox.showwarning("Campo vacío", "El título de la tarea no puede estar vacío.")


        def cancelar():
            top.destroy()


        top = Toplevel(self)
        top.title("Nueva Tarea")
        top.geometry("300x350")


        tb.Label(top, text="Título de la tarea:").pack(pady=5)
        entry_titulo = tb.Entry(top)
        entry_titulo.pack(pady=5)


        tb.Label(top, text="Descripción:").pack(pady=5)
        entry_desc = tb.Entry(top)
        entry_desc.pack(pady=5)


        tb.Label(top, text="Fecha (YYYY-MM-DD):").pack(pady=5)
        entry_fecha = tb.Entry(top)
        entry_fecha.pack(pady=5)


        btn_frame = tb.Frame(top)
        btn_frame.pack(pady=15)


        tb.Button(btn_frame, text="💾 Guardar", bootstyle="primary", command=guardar).pack(side=LEFT, padx=5)
        tb.Button(btn_frame, text="❌ Cancelar", bootstyle="danger", command=cancelar).pack(side=RIGHT, padx=5)


    def editar_tarea(self, tarea_id):
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT titulo, descripcion, fecha, estado FROM tareas WHERE id = ?", (tarea_id,))
        tarea = cur.fetchone()
        conn.close()

        if not tarea:
          return

        def guardar():
            nuevo_titulo = entry_titulo.get().strip()
            nueva_desc = entry_desc.get().strip()
            nueva_fecha = entry_fecha.get().strip()

            if nuevo_titulo:
               conn = get_connection()
               cur = conn.cursor()
               cur.execute("UPDATE tareas SET titulo=?, descripcion=?, fecha=? WHERE id=?",
                        (nuevo_titulo, nueva_desc, nueva_fecha, tarea_id))
               conn.commit()
               conn.close()
               top.destroy()
               self.recargar_tablero()
            else:
               messagebox.showwarning("Campo vacío", "El título de la tarea no puede estar vacío.")

        top = Toplevel(self)
        top.title("Editar Tarea")
        top.geometry("300x300")

        tb.Label(top, text="Título:").pack(pady=5)
        entry_titulo = tb.Entry(top)
        entry_titulo.insert(0, tarea[0])
        entry_titulo.pack(pady=5, fill=X, padx=10)

        tb.Label(top, text="Descripción:").pack(pady=5)
        entry_desc = tb.Entry(top)
        entry_desc.insert(0, tarea[1])
        entry_desc.pack(pady=5, fill=X, padx=10)

        tb.Label(top, text="Fecha (YYYY-MM-DD):").pack(pady=5)
        entry_fecha = tb.Entry(top)
        entry_fecha.insert(0, tarea[2])
        entry_fecha.pack(pady=5, fill=X, padx=10)

        btn_frame = tb.Frame(top)
        btn_frame.pack(side=BOTTOM, pady=15, fill=X, padx=10)

        tb.Button(btn_frame, text="💾 Guardar", bootstyle="primary", command=guardar).pack(side=LEFT, expand=True, fill=X, padx=5)
        tb.Button(btn_frame, text="❌ Cancelar", bootstyle="danger", command=top.destroy).pack(side=RIGHT, expand=True, fill=X, padx=5)
    
    def editar_nombre_tablero(self, tablero_id):
        nombre_actual = self.obtener_nombre_tablero(tablero_id)
        nuevo_nombre = simpledialog.askstring("Editar Tablero", "Nuevo nombre:", initialvalue=nombre_actual)
        if nuevo_nombre and nuevo_nombre.strip():
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("UPDATE tableros SET nombre=? WHERE id=?", (nuevo_nombre.strip(), tablero_id))
            conn.commit()
            conn.close()
            self.cargar_tableros()


    def editar_nombre_lista(self, lista_id):
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT nombre, tablero_id FROM listas WHERE id = ?", (lista_id,))
        fila = cur.fetchone()
        conn.close()
        if not fila:
            return
        nombre_actual, tablero_id = fila
        nuevo_nombre = simpledialog.askstring("Editar Lista", "Nuevo nombre:", initialvalue=nombre_actual)
        if nuevo_nombre and nuevo_nombre.strip():
            conn = get_connection()
            cur = conn.cursor()
            cur.execute("UPDATE listas SET nombre=? WHERE id=?", (nuevo_nombre.strip(), lista_id))
            conn.commit()
            conn.close()
            self.mostrar_tablero(tablero_id, self.obtener_nombre_tablero(tablero_id))


    def cambiar_estado_tarea(self, tarea_id):
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT estado FROM tareas WHERE id = ?", (tarea_id,))
        fila = cur.fetchone()
        if fila:
            nuevo_estado = "completada" if fila[0] == "pendiente" else "pendiente"
            cur.execute("UPDATE tareas SET estado=? WHERE id=?", (nuevo_estado, tarea_id))
            conn.commit()
        conn.close()
        self.recargar_tablero()


    def recargar_tablero(self):
        if self.tablero_actual_id:
            nombre = self.obtener_nombre_tablero(self.tablero_actual_id)
            self.mostrar_tablero(self.tablero_actual_id, nombre)
        else:
            self.cargar_tableros()


    def obtener_nombre_tablero(self, tablero_id):
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT nombre FROM tableros WHERE id = ?", (tablero_id,))
        fila = cur.fetchone()
        conn.close()
        return fila[0] if fila else ""


    def filtrar_tareas_por_fecha(self):
        if not self.tablero_actual_id:
            return


        conn = get_connection()
        cur = conn.cursor()
        query = """
            SELECT t.id, t.titulo, t.descripcion, t.fecha, t.estado, l.id, l.nombre
            FROM tareas t
            JOIN listas l ON t.lista_id = l.id
            WHERE l.tablero_id = ?
            ORDER BY date(t.fecha) ASC
        """
        cur.execute(query, (self.tablero_actual_id,))
        tareas = cur.fetchall()
        conn.close()


        for widget in self.tablero_frame.winfo_children():
            widget.destroy()


        tb.Label(self.tablero_frame,
                 text=f"Tareas ordenadas por fecha para el tablero: {self.obtener_nombre_tablero(self.tablero_actual_id)}",
                 font=("Arial", 16, "bold")).pack(pady=10)


        for tarea in tareas:
            tid, titulo, desc, fecha, estado, lista_id, lista_nombre = tarea
            estado_icon = "✅" if estado == "completada" else "⬜"
            texto = f"[{lista_nombre}] {titulo}\n{desc}\n📅 {fecha}\n{estado_icon}"
            frame = tb.Frame(self.tablero_frame, padding=5, bootstyle="info")
            frame.pack(fill=X, padx=10, pady=5)


            tb.Label(frame, text=texto, anchor=W, justify=LEFT).pack(side=LEFT, fill=X, expand=YES)


            btns = tb.Frame(frame)
            btns.pack(side=RIGHT)


            tb.Button(btns, text="✔️ Cambiar Estado", bootstyle="secondary",
                      command=lambda tid=tid: self.cambiar_estado_tarea(tid)).pack(side=LEFT, padx=2)
            tb.Button(btns, text="✏️ Modificar Tarea", bootstyle="warning",
                      command=lambda tid=tid: self.editar_tarea(tid)).pack(side=LEFT, padx=2)


        tb.Button(self.tablero_frame, text="🔙 Volver al tablero", bootstyle="info", command=self.recargar_tablero).pack(pady=10)


    def buscar_tablero(self):
        texto = self.entry_busqueda.get().strip()
        if not texto:
            self.cargar_tableros()
            return
        conn = get_connection()
        cur = conn.cursor()
        cur.execute("SELECT id, nombre FROM tableros WHERE usuario_id = ? AND nombre LIKE ?", (self.usuario_id, f"%{texto}%"))
        resultados = cur.fetchall()
        conn.close()


        for widget in self.tablero_frame.winfo_children():
            widget.destroy()


        if not resultados:
            tb.Label(self.tablero_frame, text="No se encontraron tableros.", font=("Arial", 16), bootstyle="warning").pack(pady=20)
            return


        for tablero_id, nombre in resultados:
            tb.Button(self.tablero_frame, text=nombre, bootstyle="info outline", width=30,
                      command=lambda tid=tablero_id, n=nombre: self.mostrar_tablero(tid, n)).pack(pady=5)