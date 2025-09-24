package Backend;

import java.io.BufferedReader;
import java.util.List;

import Frontend.InicioSesion;

import java.io.*;

public class MainApp {
	private static ConectorBD conexion = new ConectorBD();
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {

		InicioSesion ventanaLogin = new InicioSesion();
		ventanaLogin.mostrarVentana();

	}

}
