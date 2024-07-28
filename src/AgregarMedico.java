import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarMedico extends JFrame {
    private JButton agregarMedicoButton;
    private JButton volverAlMenuButton;
    private JTextField nombre;
    private JPasswordField contraseña;
    private JPanel panelAgregar;

    public AgregarMedico(){
        super("Agregar Medico");
        setContentPane(panelAgregar);

        volverAlMenuButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuMedico menuMedico = new MenuMedico();
                menuMedico.iniciar();
                dispose();
            }
        });
        agregarMedicoButton.addActionListener(new ActionListener() {
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
    }

    public void iniciar(){
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     *
     * @throws SQLException
     */
    public void ingresar() throws SQLException {
        Connection connection = conexion();
        String rol = "personal medico";
        String query = "INSERT INTO Usuario (nombre, contraseña, rol) VALUES(?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,nombre.getText());
        pstmt.setString(2, contraseña.getText());
        pstmt.setString(3,rol);
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han ingresado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha ingresado ningun registro");
        }
        nombre.setText("");
        contraseña.setText("");
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root2";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
}
