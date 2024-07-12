import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InicioSesion extends JFrame{
    private JPanel inicioSesionPanel;
    private JTextField usuario;
    private JTextField contraseña;
    private JButton iniciarSesionButton;
    private JLabel usLabel;
    private JLabel contrLabel;

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
                    Registro registro =  new Registro();
                    registro.iniciar();
                    dispose();
                }else {
                    JOptionPane.showMessageDialog(null,"Inicio de sesion incorrecto");
                    usuario.setText("");
                    contraseña.setText("");
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
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
    public boolean validarUsuario(String usuario, String contraseña){
        try {
            Connection connection = conexion();
            String query = "SELECT * FROM USUARIO WHERE username = ? and password = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,usuario);
            pstmt.setString(2,contraseña);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.getResultSet();
            if(resultSet.next()){
                connection.close();
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
