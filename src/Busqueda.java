import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Clase para Buscar Pacientes por el numero de cedula
 */
public class Busqueda extends JFrame {
    private JPanel panelBusqueda;
    private JTextField cedula;
    private JButton buscarButton;
    private JLabel Resultado;
    private JButton volverAlMenuButton;
    public Busqueda(){
        super("Busqueda de pacientes");
        setContentPane(panelBusqueda);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscar(conexion());
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
    /**
     * Metodo para buscar pacientes
     * @param connection
     * @throws SQLException
     */
    public void buscar(Connection connection) throws SQLException {
        String query = "SELECT * FROM PACIENTE WHERE cedula = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,cedula.getText());
        pstmt.executeQuery();
        ResultSet resultSet = pstmt.getResultSet();
        if(resultSet.next()){
            StringBuilder datos = new StringBuilder("<html>");
            datos.append("<b>Cedula:</b> ").append(resultSet.getString("cedula")).append("<br>");
            datos.append("<b>N° Historial Clinico:</b> ").append(resultSet.getInt("n_historial_clinico")).append("<br>");
            datos.append("<b>Nombre:</b> ").append(resultSet.getString("nombre")).append("<br>");
            datos.append("<b>Apellido:</b> ").append(resultSet.getString("apellido")).append("<br>");
            datos.append("<b>Teléfono:</b> ").append(resultSet.getString("telefono")).append("<br>");
            datos.append("<b>Edad:</b> ").append(resultSet.getInt("edad")).append("<br>");
            datos.append("<b>Descripcion Enfermedad:</b> ").append(resultSet.getString("descripcion_enfermedad")).append("<br>");
            datos.append("</html>");
            Resultado.setText(datos.toString());
        }else {
            Resultado.setText("Paciente NO Encontrado");
            cedula.setText("");
        }
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
}
