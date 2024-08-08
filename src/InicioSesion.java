import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Inicio de Sesion con Base de Datos en la nube
 * @author Joseph Caza
 */
public class InicioSesion extends JFrame{
    private JPanel inicioSesionPanel;
    private JTextField usuario;
    private JPasswordField contraseña;
    private JButton iniciarSesionButton;
    private JLabel usLabel;
    private JLabel contrLabel;

    /**
     * Metodo constructor
     */
    public InicioSesion() {
        super("Inicio de Sesion");
        setContentPane(inicioSesionPanel);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioIngresado = usuario.getText();
                String contraIngresada = contraseña.getText();
                if(validarUsuario(usuarioIngresado,contraIngresada)){
                    JOptionPane.showMessageDialog(null,"Inicio de sesion correcto");
                }else {
                    JOptionPane.showMessageDialog(null,"Inicio de sesion incorrecto");
                    usuario.setText("");
                    contraseña.setText("");
                }
            }
        });
    }

    /**
     * Metodo que define las caracteristicas de la pantalla
     */
    public void iniciar(){
        setVisible(true);
        setSize(500,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://ujkhbignzbkn6drb:wt4yA7DMNsjsESDuPDYj@bzur1xo4hnmfvo0nrqz6-mysql.services.clever-cloud.com:3306/bzur1xo4hnmfvo0nrqz6";
        String user = "ujkhbignzbkn6drb";
        String password = "wt4yA7DMNsjsESDuPDYj";
        return DriverManager.getConnection(url,user,password);
    }
    public boolean validarUsuario(String usuario, String contraseña){
        try {
            Connection connection = conexion();
            String query = "SELECT * FROM Usuario WHERE usuario = ? and contraseña = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,usuario);
            pstmt.setString(2,contraseña);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if(resultSet.next()){
                String rolUsuario = resultSet.getString("rol");
                connection.close();

                if (rolUsuario.equalsIgnoreCase("administrador")) {
                    PantallaAdministrador pantallaAdministrador = new PantallaAdministrador();
                    pantallaAdministrador.iniciar();
                } else if (rolUsuario.equalsIgnoreCase("personal medico")) {
                    PantallaPersonalMedico pantallaPersonalMedico = new PantallaPersonalMedico();
                    pantallaPersonalMedico.iniciar();
                } else {
                    JOptionPane.showMessageDialog(null, "Rol de usuario no válido");
                }

                dispose();
                return true;
            }
            else {
                connection.close();
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }


}
