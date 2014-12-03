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

import es.albarregas.Modelo.Clientes;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ventura
 */
public class ClientesDAO extends Conexion {

    public ArrayList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        String query = "select *  from clientes";
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Clientes client = new Clientes();
                client.setId(resultado.getInt("id"));
                client.setNombre(resultado.getString("nombre"));
                client.setApellidos(resultado.getString("apellidos"));
                client.setNif_nie(resultado.getString("nif_nie"));
                client.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                client.setDireccion(resultado.getString("direccion"));
                lista.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;
    }

    public ArrayList<Clientes> getDatosClientes(int id) {
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("select * from clientes where id = ?");
            sentencia.setInt(1, id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Clientes client = new Clientes();
                client.setId(resultado.getInt("id"));
                client.setNombre(resultado.getString("nombre"));
                client.setApellidos(resultado.getString("apellidos"));
                client.setNif_nie(resultado.getString("nif_nie"));
                client.setFechaNacimiento(resultado.getDate("fechaNacimiento"));
                client.setDireccion(resultado.getString("direccion"));
                lista.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;
    }
    
    public void insertarCliente(Clientes cliente, int id){
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("insert into clientes values( ? ,? ,? ,? ,? ,?, ?)");
            sentencia.setInt(1, id);
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getApellidos());
            sentencia.setString(4, cliente.getNif_nie());
            sentencia.setDate(5,new java.sql.Date(cliente.getFechaNacimiento().getTime()));
            sentencia.setString(6, cliente.getDireccion());
            sentencia.setString(7, cliente.getSexo());
            sentencia.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        
    }

    public void modificarCliente(Clientes cliente) {
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement("update clientes set nombre = ? , apellidos = ? , fechaNacimiento = ? , sexo= ?, direccion = ? where clientes.nif_nie = ?");
            sentencia.setString(1, cliente.getNombre());
            sentencia.setString(2, cliente.getApellidos());
            sentencia.setDate(3,new java.sql.Date(cliente.getFechaNacimiento().getTime()));
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(4, cliente.getSexo());
            sentencia.setString(6, cliente.getNif_nie());
            sentencia.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

}
