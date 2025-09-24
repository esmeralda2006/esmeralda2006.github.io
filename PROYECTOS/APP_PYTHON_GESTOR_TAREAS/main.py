import ttkbootstrap as tb

# from ttkbootstrap.constants import *  # Removed due to unresolved import
from database import init_db
from login import LoginWindow
from panel import Dashboard

# Función que se ejecuta al iniciar sesión correctamente
def iniciar_dashboard(usuario_id):
    # Cierra la ventana de login y abre el panel principal
    root.destroy()
    app = Dashboard(usuario_id)
    app.mainloop()

# Punto de entrada principal de la aplicación
if __name__ == "__main__":
    # Inicializar la base de datos (crear tablas si no existen)
    init_db()

    # Crear ventana principal de login
    root = tb.Window(themename="darkly")
    root.title("Gestor de Tareas - Inicio de Sesión")
    LoginWindow(root, iniciar_dashboard)
    root.mainloop()