import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para eliminar Registros de Pacientes de la base de datos
 */
public class Eliminar extends JFrame{
    private JTextField cedula;
    private JButton eliminarRegistroButton;
    private JButton volverAlMenuButton;
    private JPanel panelEliminar;

    public Eliminar() {
        super("Eliminar Registros");
        setContentPane(panelEliminar);
        eliminarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminar();
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
    public void eliminar() throws SQLException {
        Connection connection = conexion();
        String query = "DELETE FROM PACIENTE WHERE cedula=?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, cedula.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null, "Se ha eliminado el registro correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se ha eliminado ningun registro");
        }
        cedula.setText("");
    }

}
