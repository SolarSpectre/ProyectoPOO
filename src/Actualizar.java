import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Actualizar extends JFrame{
    private JButton actualizarPacienteButton;
    private JButton volverAlMenuButton;
    private JTextField cedula;
    private JTextField historial;
    private JTextField nombre;
    private JTextField apellido;
    private JTextField telefono;
    private JTextField edad;
    private JTextField descripcion;
    private JPanel panelBuscar;

    public Actualizar() {
        super("Actualizar Registros");
        setContentPane(panelBuscar);
        volverAlMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPacientes menuPacientes = new MenuPacientes();
                menuPacientes.iniciar();
                dispose();
            }
        });
        actualizarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void actualizar() throws SQLException {
        Connection connection = conexion();
        String query = "UPDATE PACIENTE SET n_historial_clinico=?, nombre=?, apellido=?, telefono=?, edad=?, descripcion_enfermedad=? WHERE cedula=?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(historial.getText()));
        pstmt.setString(2, nombre.getText());
        pstmt.setString(3, apellido.getText());
        pstmt.setString(4, telefono.getText());
        pstmt.setInt(5, Integer.parseInt(edad.getText()));
        pstmt.setString(6, descripcion.getText());
        pstmt.setString(7, cedula.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han actualizado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha actualizado ningun registro");
        }
        cedula.setText("");
        historial.setText("");
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        edad.setText("");
        descripcion.setText("");
    }

    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root";
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
