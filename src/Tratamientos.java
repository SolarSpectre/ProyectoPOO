import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tratamientos {
    private ConexionBDD_Local conexionBDDLocal;

    public Tratamientos(ConexionBDD_Local conexionBDDLocal) {
        this.conexionBDDLocal = conexionBDDLocal;
    }

    public void ingresar(Integer historialMedico, String medicamento, String dosis, String duracion) {
        String query = "INSERT INTO Tratamiento (id_historial_medico, medicamento, dosis, duracion) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = conexionBDDLocal.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, historialMedico);
            pstmt.setString(2, medicamento);
            pstmt.setString(3, dosis);
            pstmt.setString(4, duracion);

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
                if (connection != null) conexionBDDLocal.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

