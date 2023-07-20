package practica1.Servicios;

import practica1.Clases.RegistroUsuario;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionService {
    private static String URL = "jdbc:postgresql://snobby-minion-4230.g8z.cockroachlabs.cloud:26257/db_app";
    private static String user = "rodolfo";
    private static String password = "Z6NT61pvNNvYrcGZvzK9Bw";
    private static ConexionService instancia;

    public ConexionService() {
        regDriver();
    }

    public static ConexionService getInstancia(){
        if(instancia == null){
            instancia=new ConexionService();
        }
        return instancia;
    }

    private void regDriver() {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException exception){
            Logger.getLogger(ConexionService.class.getName()).log(Level.SEVERE, null, exception);

        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL,user, password);
        } catch (SQLException ex) {
            System.err.println("No se logro conectar a la base de datos");
        }
        return con;
    }

    public void executeDB(String sql) throws SQLException {
        Connection con =  this.getConexion();
        Statement stmt = con.createStatement();
        stmt.execute(sql);
        con.close();
    }

    public void insertRegistroUsuario(RegistroUsuario r) throws SQLException {
        String sql = "INSERT INTO REGISTROUSUARIO (FECHA_REGISTRO, USERNAME) VALUES ('" + new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha_registro()) + "', '" + r.getUsername() + "');";
        this.executeDB(sql);
    }

    public void crearTabla() throws SQLException {
//        String sql_reg = "DROP TABLE REGISTROUSUARIO";

        String sql_reg = "CREATE TABLE IF NOT EXISTS REGISTROUSUARIO\n" +
                "(\n" +
                "  ID SERIAL PRIMARY KEY,\n" +
                "  FECHA_REGISTRO DATE NOT NULL,\n" +
                "  USERNAME VARCHAR(100) NOT NULL\n" +
                ");";

        this.executeDB(sql_reg);
    }

}
