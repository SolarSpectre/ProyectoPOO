import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

    public PantallaPersonalMedico() {
        super("Menu Personal Medico");
        setContentPane(menuMedicos);
        registrarTratamientoButton.addActionListener(new ActionListener() {
            /**
             * Invocado al hacer clic en el boton de registrar tratamiento
             *
             * @param e el evento al hacer clic en el boton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexionBDD_Local conexion = null;
                try {
                    conexion = new ConexionBDD_Local();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                Tratamientos tratamientos = new Tratamientos(conexion);
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
                ConexionBDD_Local conexion = null;
                try {
                    conexion = new ConexionBDD_Local();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                Citas citas = new Citas(conexion);
                citas.ingresar(cedula_paciente.getText(),Integer.parseInt(id_usuario.getText()), fecha.getText(), motivo.getText());
                cedula_paciente.setText("");
                id_usuario.setText("");
                fecha.setText("");
                motivo.setText("");
            }
        });
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
