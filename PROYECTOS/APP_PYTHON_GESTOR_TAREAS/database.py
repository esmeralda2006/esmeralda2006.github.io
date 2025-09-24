import sqlite3

def get_connection():
    return sqlite3.connect("app.db")

def init_db():
    conn = get_connection()
    cursor = conn.cursor()

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL UNIQUE,
            password TEXT NOT NULL
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS tableros (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            usuario_id INTEGER,
            FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS listas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT NOT NULL,
            tablero_id INTEGER,
            FOREIGN KEY (tablero_id) REFERENCES tableros(id) ON DELETE CASCADE
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS tareas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT NOT NULL,
            descripcion TEXT,
            fecha TEXT,
            estado TEXT DEFAULT 'pendiente',
            lista_id INTEGER,
            FOREIGN KEY (lista_id) REFERENCES listas(id) ON DELETE CASCADE
        )
    """)

    conn.commit()
    conn.close()

class Database:
    def verificar_usuario(self, username, password):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id FROM usuarios WHERE username = ? AND password = ?", (username, password))
            return cur.fetchone()

    def usuario_existe(self, username):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id FROM usuarios WHERE username = ?", (username,))
            return cur.fetchone()

    def registrar_usuario(self, username, password):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("INSERT INTO usuarios (username, password) VALUES (?, ?)", (username, password))
            conn.commit()

    def obtener_tableros(self, usuario_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id, nombre FROM tableros WHERE usuario_id = ?", (usuario_id,))
            return cur.fetchall()

    def crear_tablero(self, nombre, usuario_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("INSERT INTO tableros (nombre, usuario_id) VALUES (?, ?)", (nombre, usuario_id))
            conn.commit()

    def actualizar_nombre_tablero(self, tablero_id, nuevo_nombre):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("UPDATE tableros SET nombre = ? WHERE id = ?", (nuevo_nombre, tablero_id))
            conn.commit()

    def buscar_tableros(self, usuario_id, filtro):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id, nombre FROM tableros WHERE usuario_id = ? AND nombre LIKE ?", 
                        (usuario_id, f'%{filtro}%'))
            return cur.fetchall()

    def eliminar_tablero(self, tablero_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("DELETE FROM tableros WHERE id = ?", (tablero_id,))
            conn.commit()

    def obtener_listas(self, tablero_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id, nombre FROM listas WHERE tablero_id = ?", (tablero_id,))
            return cur.fetchall()

    def crear_lista(self, nombre, tablero_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("INSERT INTO listas (nombre, tablero_id) VALUES (?, ?)", (nombre, tablero_id))
            conn.commit()

    def actualizar_nombre_lista(self, lista_id, nuevo_nombre):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("UPDATE listas SET nombre = ? WHERE id = ?", (nuevo_nombre, lista_id))
            conn.commit()

    def eliminar_lista(self, lista_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("DELETE FROM listas WHERE id = ?", (lista_id,))
            conn.commit()

    def obtener_tareas(self, lista_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("SELECT id, titulo, descripcion, fecha, estado FROM tareas WHERE lista_id = ?", (lista_id,))
            return cur.fetchall()

    def crear_tarea(self, titulo, descripcion, fecha, lista_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("""
                INSERT INTO tareas (titulo, descripcion, fecha, estado, lista_id)
                VALUES (?, ?, ?, 'pendiente', ?)
            """, (titulo, descripcion, fecha, lista_id))
            conn.commit()

    def actualizar_tarea(self, tarea_id, titulo, descripcion, fecha, estado):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("""
                UPDATE tareas
                SET titulo = ?, descripcion = ?, fecha = ?, estado = ?
                WHERE id = ?
            """, (titulo, descripcion, fecha, estado, tarea_id))
            conn.commit()

    def eliminar_tarea(self, tarea_id):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("DELETE FROM tareas WHERE id = ?", (tarea_id,))
            conn.commit()

    def actualizar_estado_tarea(self, tarea_id, nuevo_estado):
        with get_connection() as conn:
            cur = conn.cursor()
            cur.execute("UPDATE tareas SET estado = ? WHERE id = ?", (nuevo_estado, tarea_id))
            conn.commit()