import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que tiene la conexion a la base de datos local para todos los metodos
 */
public class ConexionBDD_Local {

    private Connection connection;
    /**
     * No cambiar los parametros
     */
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_hospitalario";
    private static final String USER = "root2";
    private static final String PASSWORD = "12345";

    /**
     * retorna la conexion que genera el metodo crearConexion
     * @throws SQLException
     */
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
