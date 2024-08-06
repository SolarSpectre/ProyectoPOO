import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Menu que se ejecuta al iniciar sesion como personal medico
 */
public class PantallaPersonalMedico extends JFrame{
    private JPanel menuMedicos;
    private JTabbedPane tabbedPane1;
    private JButton registrarCitaButton;
    private JTextField cedula_paciente;
    private JTextField id_usuario;
    private JTextField fecha;
    private JTextField motivo;
    private JButton registrarTratamientoButton;
    private JTextField medicamento;
    private JTextField dosis;
    private JTextField duracion;
    private JTextField historial_medico;
    private JTextField historialR;
    private JTextField examenR;
    private JTextField resultadoR;
    private JTextField fechaR;
    private JButton registrarResultadoButton;
    ConexionBDD_Local conexionBDDLocal;

    public PantallaPersonalMedico() {
        super("Menu Personal Medico");
        setContentPane(menuMedicos);

        try {
            conexionBDDLocal = new ConexionBDD_Local();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        registrarTratamientoButton.addActionListener(new ActionListener() {
            /**
             * Invocado al hacer clic en el boton de registrar tratamiento
             *
             * @param e el evento al hacer clic en el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Tratamientos tratamientos = new Tratamientos(conexionBDDLocal);
                tratamientos.ingresar(Integer.parseInt(historial_medico.getText()),medicamento.getText(), dosis.getText(), duracion.getText());
                historial_medico.setText("");
                medicamento.setText("");
                dosis.setText("");
                duracion.setText("");
            }
        });
        registrarCitaButton.addActionListener(new ActionListener() {
            /**
             * Invocado al hacer clic en el boton de registrar cita
             *
             * @param e el evento al hacer clic en el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Citas citas = new Citas(conexionBDDLocal);
                    citas.ingresar(cedula_paciente.getText(), Integer.parseInt(id_usuario.getText()), fecha.getText(), motivo.getText());
                    cedula_paciente.setText("");
                    id_usuario.setText("");
                    fecha.setText("");
                    motivo.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar la cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registrarResultadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarResultado();
            }
        });
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void ingresarResultado() {
        String query = "INSERT INTO Resultado_Examen (id_historial_medico, tipo_examen, resultado, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = conexionBDDLocal.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(historialR.getText()));
            pstmt.setString(2, examenR.getText());
            pstmt.setString(3, resultadoR.getText());
            pstmt.setDate(4, Date.valueOf(fechaR.getText())); // Assuming fechaR is a JTextField

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Se han ingresado los datos correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha ingresado ningun registro");
            }

            historialR.setText("");
            examenR.setText("");
            resultadoR.setText("");
            fechaR.setText("");
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
