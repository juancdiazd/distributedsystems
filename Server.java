import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345; // Cambiar al puerto en el que el servidor escuchará

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en el puerto " + port);

            while (true) {
                try (Socket clienteSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)) {

                    // Recibe el número de teléfono del cliente
                    String telefono = in.readLine();

                    // Busca la información de la persona en la base de datos
                    String personaInfo = buscarPersonaPorTelefono(telefono);

                    // Envia la respuesta al cliente
                    out.println(personaInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buscarPersonaPorTelefono(String telefono) {
        String dbUrl = "jdbc:mysql://localhost/base_de_datos"; // Cambiar al puerto en el que el servidor escuchará
        String dbUser = "usuario";
        String dbPassword = "contraseña";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT dir_tel, dir_nombre, dir_direccion, ciud_nombre FROM personas " +
                                "INNER JOIN ciudades ON personas.dir_ciud_id = ciudades.ciud_id " +
                                "WHERE dir_tel = ?")) {

            statement.setString(1, telefono);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String telefonoResult = resultSet.getString("dir_tel");
                String nombre = resultSet.getString("dir_nombre");
                String direccion = resultSet.getString("dir_direccion");
                String ciudad = resultSet.getString("ciud_nombre");

                return "Número de teléfono: " + telefonoResult + "\nNombre: " + nombre +
                        "\nDirección: " + direccion + "\nCiudad: " + ciudad;
            } else {
                return "Persona dueña de ese número telefónico no existe.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al consultar la base de datos.";
        }
    }
}