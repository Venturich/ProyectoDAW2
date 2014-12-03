/*
 *  
 *  	Proyecto para:
 *  	DI DWC DWE
 *  
 *  	Autor:
 * 	Ventura Preciado S�nchez
 * 
 * 	DAW2 IES ALBARREGAS
 * 
 */
package es.albarregas.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Ventura
 */
public class Conexion {

    protected Connection conexion;
    protected PreparedStatement sentencia;
    protected ResultSet resultado;
    protected static final String DATASOURCE_NAME = "java:comp/env/jdbc/proyectoDAW2";

    public Conexion() {

    }

    public void iniciarConexion() {
        if (conexion == null) {
            try {
                Context initialContext = new InitialContext();
                DataSource datasource
                        = (DataSource) initialContext.lookup(DATASOURCE_NAME);
                //= (DataSource) initialContext.lookup("jdbc/MySQLDataSource");
                conexion = datasource.getConnection();
            } catch (NamingException ex) {
                System.out.println("Problemas en el acceso al recurso...");
                ex.printStackTrace();

            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public void cerrarConexion() {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}


