import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
/**
 * Ver Reportes Estadisticos en el modulo de Administrador
 *
 */
public class VerReportes extends JFrame{
    private JPanel verCitasPanel;
    private JTable citas;
    private JTable tratamientos;
    private JButton volverButton;

    public VerReportes() throws SQLException {
        super("Ver Citas");
        Connection connection = conexion();
        agregarTablaCitas(connection);
        agregarTablaTratamientos(connection);

        setContentPane(verCitasPanel);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuMedico menuMedico = new MenuMedico();
                menuMedico.iniciar();
                dispose();
            }
        });
    }

    /**
     * Metodo que inicia los atributos que tendra la ventana
     */
    public void iniciar(){
        setVisible(true);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     *
     * @return La conexion a la base de datos
     * @throws SQLException
     */
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root2";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }

    /**
     *
     * @param connection
     * @throws SQLException
     */
    private void agregarTablaCitas(Connection connection) throws SQLException {
        String query = "SELECT * FROM Reporte_Citas_Por_Medico";
        String[] columnas = {"Medico", "Total Citas"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        model.addRow(columnas);
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] fila = {rs.getString("medico"), String.valueOf(rs.getInt("total_citas"))};
                model.addRow(fila);
            }
        }
        citas.setModel(model);
    }

    /**
     *
     * @param connection
     * @throws SQLException
     */
    private void agregarTablaTratamientos(Connection connection) throws SQLException {
        String query = "SELECT * FROM Reporte_Pacientes_Tratamientos";
        String[] columnas = {"Cedula", "Nombre", "Apellido", "Medicamento", "Dosis", "Duracion"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        model.addRow(columnas);
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] fila = {
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("medicamento"),
                        rs.getString("dosis"),
                        rs.getString("duracion")
                };
                model.addRow(fila);
            }
        }

        tratamientos.setModel(model);
    }

}
