import ttkbootstrap as ttk
from tkinter import messagebox
from database import Database

class LoginWindow:
    def __init__(self, root, app_callback):
        self.db = Database()
        self.app_callback = app_callback
        self.root = root

        self.root.title("Inicio de Sesión - Trello App")
        self.root.geometry("400x350")
        self.root.resizable(False, False)

        ttk.Label(self.root, text="APP GESTIÓN DE TAREAS", font=("Helvetica", 16, "bold")).pack(pady=20)

        ttk.Label(self.root, text="Usuario:").pack()
        self.username_entry = ttk.Entry(self.root, width=30)
        self.username_entry.pack(pady=5)

        ttk.Label(self.root, text="Contraseña:").pack()
        self.password_entry = ttk.Entry(self.root, show="*", width=30)
        self.password_entry.pack(pady=5)

        ttk.Button(self.root, text="Iniciar Sesión", width=20, bootstyle="success", command=self.login).pack(pady=15)
        ttk.Button(self.root, text="Registrarse", width=20, bootstyle="info", command=self.register).pack()

    def login(self):
        username = self.username_entry.get().strip()
        password = self.password_entry.get().strip()

        if not username or not password:
            return messagebox.showwarning("Campos Vacíos", "Por favor, ingrese usuario y contraseña.")
        
        try:
            user = self.db.verificar_usuario(username, password)
            if user:
                # No destruimos aquí la ventana, el callback se encargará de eso
                self.app_callback(user[0])  # Llamamos al callback con el id del usuario
            else:
                messagebox.showerror("Error de Inicio de Sesión", "Credenciales incorrectas.")
        except Exception as e:
            messagebox.showerror("Error", f"Ocurrió un error:\n{e}")

    def register(self):
        username = self.username_entry.get().strip()
        password = self.password_entry.get().strip()

        if not username or not password:
            return messagebox.showwarning("Campos Vacíos", "Por favor, ingrese usuario y contraseña.")

        try:
            if self.db.usuario_existe(username):
                return messagebox.showwarning("Usuario Existente", "El nombre de usuario ya está en uso.")

            self.db.registrar_usuario(username, password)
            messagebox.showinfo("Registro Exitoso", "Usuario registrado correctamente. Ahora puede iniciar sesión.")
            self.username_entry.delete(0, 'end')
            self.password_entry.delete(0, 'end')
        except Exception as e:
            messagebox.showerror("Error", f"No se pudo registrar el usuario:\n{e}")