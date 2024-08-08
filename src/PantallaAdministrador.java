import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Menu que se ejecuta al iniciar sesion como administrador
 */
public class PantallaAdministrador extends JFrame{
    private JPanel menuAdmin;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTable citas;
    private JTable tratamientos;
    private JTextField nombre;
    private JTextField contraseña;
    private JButton agregarMedicoButton;
    private JTabbedPane tabbedPane3;
    private JButton ingresarPacienteButton;
    private JTextField cedulaI;
    private JTextField historialI;
    private JTextField nombreI;
    private JTextField apellidoI;
    private JTextField telefonoI;
    private JTextField edadI;
    private JTextField enfermedadI;
    private JButton actualizarPacienteButton;
    private JTextField cedulaA;
    private JTextField historiaA;
    private JTextField nombreA;
    private JTextField apellidoA;
    private JTextField telefonoA;
    private JTextField edadA;
    private JTextField enfermedadA;
    private JTextField cedulaB;
    private JButton buscarButton;
    private JLabel Resultado;
    private JTextField cedulaE;
    private JButton eliminarRegistroButton;
    private JTextField nombrE;
    private JButton eliminarButton;
    private JTable examenes;

    private Connection connection;

    public PantallaAdministrador() {
        super("Menu Administrador");
        setContentPane(menuAdmin);
        try {
            ConexionBDD_Local conexionBDD_local = new ConexionBDD_Local();
            this.connection = conexionBDD_local.getConnection();

            agregarTablaCitas(connection);
            agregarTablaTratamientos(connection);
            agregarTablaResultados(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        agregarMedicoButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ingresarMedico();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ingresarPacienteButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ingresarPaciente();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        actualizarPacienteButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizar(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscar(connection);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        eliminarRegistroButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarMedico();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
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
    private void agregarTablaResultados(Connection connection) throws SQLException {
        String query = "SELECT * FROM Resultado_Examen";
        String[] columnas = {"id_historial_medico", "tipo_examen", "resultado", "fecha"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        model.addRow(columnas);
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] fila = {
                        rs.getString("id_historial_medico"),
                        rs.getString("tipo_examen"),
                        rs.getString("resultado"),
                        rs.getString("fecha")
                };
                model.addRow(fila);
            }
        }

        examenes.setModel(model);
    }
    /**
     *
     * @throws SQLException
     */
    public void ingresarMedico() throws SQLException {
        Connection connection = conexion();
        String rol = "personal medico";
        String query = "INSERT INTO Usuario (usuario, contraseña, rol) VALUES(?,?,?)";
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
    public void eliminarMedico() throws SQLException {
        Connection connection = conexion();
        String query = "DELETE FROM Usuario WHERE usuario = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, nombrE.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"El registro ha sido eliminado correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha eliminado ningun registro");
        }
        nombrE.setText("");
    }
    public Connection conexion() throws SQLException {
        String url = "jdbc:mysql://ujkhbignzbkn6drb:wt4yA7DMNsjsESDuPDYj@bzur1xo4hnmfvo0nrqz6-mysql.services.clever-cloud.com:3306/bzur1xo4hnmfvo0nrqz6";
        String user = "ujkhbignzbkn6drb";
        String password = "wt4yA7DMNsjsESDuPDYj";
        return DriverManager.getConnection(url,user,password);
    }
    public void ingresarPaciente() throws SQLException {
        String query = "INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,cedulaI.getText());
        pstmt.setInt(2,Integer.parseInt(historialI.getText()));
        pstmt.setString(3,nombreI.getText());
        pstmt.setString(4,apellidoI.getText());
        pstmt.setString(5,telefonoI.getText());
        pstmt.setInt(6,Integer.parseInt(edadI.getText()));
        pstmt.setString(7,enfermedadI.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han ingresado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha ingresado ningun registro");
        }
        cedulaI.setText("");
        historialI.setText("");
        nombreI.setText("");
        apellidoI.setText("");
        telefonoI.setText("");
        edadI.setText("");
        enfermedadI.setText("");
    }
    public void actualizar(Connection connection) throws SQLException {
        String query = "UPDATE PACIENTE SET n_historial_clinico=?, nombre=?, apellido=?, telefono=?, edad=?, descripcion_enfermedad=? WHERE cedula=?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(historiaA.getText()));
        pstmt.setString(2, nombreA.getText());
        pstmt.setString(3, apellidoA.getText());
        pstmt.setString(4, telefonoA.getText());
        pstmt.setInt(5, Integer.parseInt(edadA.getText()));
        pstmt.setString(6, enfermedadA.getText());
        pstmt.setString(7, cedulaA.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null,"Se han actualizado los datos correctamente");
        }else{
            JOptionPane.showMessageDialog(null,"No se ha actualizado ningun registro");
        }
        cedulaA.setText("");
        historiaA.setText("");
        nombreA.setText("");
        apellidoA.setText("");
        telefonoA.setText("");
        edadA.setText("");
        enfermedadA.setText("");
    }
    public void buscar(Connection connection) throws SQLException {
        String query = "SELECT * FROM PACIENTE WHERE cedula = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,cedulaB.getText());
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
            cedulaB.setText("");
        }
    }
    public void eliminar() throws SQLException {
        String query = "DELETE FROM PACIENTE WHERE cedula=?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, cedulaE.getText());
        int filas = pstmt.executeUpdate();
        if(filas > 0){
            JOptionPane.showMessageDialog(null, "Se ha eliminado el registro correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se ha eliminado ningun registro");
        }
        cedulaE.setText("");
    }
    public void iniciar(){
        setVisible(true);
        setSize(1000,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
