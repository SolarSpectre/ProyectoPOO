import javax.swing.*;
import java.sql.*;

/**
 * Clase para registrar citas medicas
 */
public class Citas {
    private ConexionBDD_Local conexionBDDLocal;

    /**
     * Obtiene la conexion de la clase conexionBDDLocal
     * @param conexionBDDLocal
     */
    public Citas(ConexionBDD_Local conexionBDDLocal) {
        this.conexionBDDLocal = conexionBDDLocal;
    }

    public void ingresar(String cedula_paciente, Integer id_usuario, String fecha, String motivo) {
        String query = "INSERT INTO Cita (cedula_paciente, id_usuario, fecha, motivo) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = conexionBDDLocal.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cedula_paciente);
            pstmt.setInt(2, id_usuario);
            pstmt.setTimestamp(3, Timestamp.valueOf(fecha)); // Assuming fecha is in 'yyyy-mm-dd hh:mm:ss' format
            pstmt.setString(4, motivo);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Se han ingresado los datos correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha ingresado ningún registro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al ingresar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
