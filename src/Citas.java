import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Citas extends JFrame {
    private JPanel panelCitas;
    private JTextField cedula_paciente;
    private JTextField id_usuario;
    private JTextField fecha;
    private JTextField motivo;
    private JButton registrarCitaButton;
    private JButton volverAlMenuButton;

    public Citas(){
        super("Citas");
        setContentPane(panelCitas);

        registrarCitaButton.addActionListener(new ActionListener() {
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
        String query = "INSERT INTO Cita (cedula_paciente, id_usuario, fecha, motivo) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,cedula_paciente.getText());
        pstmt.setInt(2,Integer.parseInt(id_usuario.getText()));
        pstmt.setString(3,fecha.getText());
        pstmt.setString(4,motivo.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han ingresado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha ingresado ningun registro");
        }
        cedula_paciente.setText("");
        id_usuario.setText("");
        fecha.setText("");
        motivo.setText("");
    }

    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root2";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
