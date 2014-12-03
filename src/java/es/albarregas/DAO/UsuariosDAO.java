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

import es.albarregas.Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ventura
 */
public class UsuariosDAO extends Conexion {

    public ArrayList<Usuarios> getUsuarios() {
        //Conexion conexionDAO = new Conexion();

        ArrayList<Usuarios> lista = new ArrayList<Usuarios>();

        try {

            iniciarConexion();
            sentencia = conexion.prepareStatement("select id, email, AES_DECRYPT(clave, 'wololo') as clave, ultimoAcceso, tipoAcceso, bloqueado from usuarios");
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Usuarios user = new Usuarios();
                user.setId(resultado.getInt("id"));
                user.setEmail(resultado.getString("email"));
                user.setClave(resultado.getString("clave"));
                user.setUltimoAcceso(resultado.getDate("ultimoAcceso"));
                user.setTipoAcceso(resultado.getString("tipoAcceso"));
                user.setBloqueado(resultado.getString("bloqueado"));
                lista.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;
    }

    public int insertarUsuario(Usuarios usuario) {
        int id = -1;
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("insert into usuarios ( email, clave)values( ?, AES_ENCRYPT(?, 'wololo'))");
            sentencia.setString(1, usuario.getEmail());
            sentencia.setString(2, usuario.getClave());
            sentencia.executeUpdate();
            if (usuario.getTipoAcceso().equals("u")) {
                sentencia = conexion.prepareStatement("select id from usuarios where email = ?");
                sentencia.setString(1, usuario.getEmail());
                resultado = sentencia.executeQuery();
                resultado.next();
                id = resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return id;
    }

    public void bloquearUsuario(Usuarios usuario) {
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("update usuarios set bloqueado = 's' where email = ?");
            sentencia.setString(1, usuario.getEmail());
            sentencia.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }
}
