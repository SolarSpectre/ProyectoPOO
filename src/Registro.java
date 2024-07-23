import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registro extends JFrame {
    private JPanel panelRegistro;
    private JButton ingresarPacienteButton;
    private JTextField cedula;
    private JTextField historialClinico;
    private JTextField nombre;
    private JTextField apellido;
    private JTextField telefono;
    private JTextField edad;
    private JTextField descripEnfermedad;
    private JButton volverAlMenuButton;

    public Registro(){
        super("Registro de Pacientes");
        setContentPane(panelRegistro);
        ingresarPacienteButton.addActionListener(new ActionListener() {
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
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPacientes menuPacientes = new MenuPacientes();
                menuPacientes.iniciar();
                dispose();
            }
        });
    }
    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void ingresar() throws SQLException {
        Connection connection = conexion();
        String query = "INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,cedula.getText());
        pstmt.setInt(2,Integer.parseInt(historialClinico.getText()));
        pstmt.setString(3,nombre.getText());
        pstmt.setString(4,apellido.getText());
        pstmt.setString(5,telefono.getText());
        pstmt.setInt(6,Integer.parseInt(edad.getText()));
        pstmt.setString(7,descripEnfermedad.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han ingresado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha ingresado ningun registro");
        }
        cedula.setText("");
        historialClinico.setText("");
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        edad.setText("");
        descripEnfermedad.setText("");
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
}
