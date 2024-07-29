import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tratamientos extends JFrame {
    private JPanel panelTratamientos;
    private JTextField medicamento;
    private JTextField historial_medico;
    private JTextField dosis;
    private JTextField duracion;
    private JButton registrarTratamientoButton;
    private JButton volverAlMenuButton;

    public Tratamientos(){
        super("Tratamientos");
        setContentPane(panelTratamientos);
        registrarTratamientoButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ingresar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        volverAlMenuButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaPersonalMedico pantallaPersonalMedico = new PantallaPersonalMedico();
                pantallaPersonalMedico.iniciar();
                dispose();
            }
        });
    }
    public void ingresar() throws SQLException {
        Connection connection = conexion();
        String query = "INSERT INTO Tratamiento (id_historial_medico, medicamento, dosis, duracion) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,Integer.parseInt(historial_medico.getText()));
        pstmt.setString(2,medicamento.getText());
        pstmt.setString(3,dosis.getText());
        pstmt.setString(4,duracion.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han ingresado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha ingresado ningun registro");
        }
        historial_medico.setText("");
        medicamento.setText("");
        dosis.setText("");
        duracion.setText("");
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root2";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
}
