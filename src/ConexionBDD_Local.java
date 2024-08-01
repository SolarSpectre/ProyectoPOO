import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD_Local {

    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_hospitalario";
    private static final String USER = "root2";
    private static final String PASSWORD = "12345";

    public ConexionBDD_Local() throws SQLException {
        this.connection = crearConexion();
    }

    private Connection crearConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
