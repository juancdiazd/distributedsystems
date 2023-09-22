import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Cambiar a la IP del servidor
        int serverPort = 12345; // Cambiar al Puerto en el que el servidor está escuchando

        try (Socket socket = new Socket(serverAddress, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Ingrese el número de teléfono: ");
            String telefono = userInput.readLine();

            // Envía el número de teléfono al servidor
            out.println(telefono);

            // Recibe y muestra la respuesta del servidor
            String response = in.readLine();
            System.out.println("Respuesta del servidor:\n" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}