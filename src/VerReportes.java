import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
    public void iniciar(){
        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_hospitalario";
        String user = "root2";
        String password = "12345";
        return DriverManager.getConnection(url,user,password);
    }
    private void agregarTablaCitas(Connection connection) throws SQLException {
        String query = "SELECT * FROM Reporte_Citas_Por_Medico";
        String[] columnas = {"Medico", "Total Citas"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] fila = {rs.getString("medico"), String.valueOf(rs.getInt("total_citas"))};
                model.addRow(fila);
            }
        }
        citas.setModel(model);
    }

    private void agregarTablaTratamientos(Connection connection) throws SQLException {
        String query = "SELECT * FROM Reporte_Pacientes_Tratamientos";
        String[] columnas = {"Cedula", "Nombre", "Apellido", "Medicamento", "Dosis", "Duracion"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

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
