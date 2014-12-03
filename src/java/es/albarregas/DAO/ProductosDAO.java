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

import es.albarregas.Modelo.Productos;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ventura
 */
public class ProductosDAO extends Conexion {

    public ArrayList<Productos> getCatalogo() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {

            iniciarConexion();
            sentencia = conexion.prepareStatement("select * from productos where bloqueado = 'n'");
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Productos pro = new Productos();
                pro.setId(resultado.getString("id"));
                pro.setDenominacion(resultado.getString("denominacion"));
                pro.setDescripcion(resultado.getString("descripcion"));
                pro.setPrecio(resultado.getDouble("precio"));
                pro.setStock(resultado.getInt("stock"));
                pro.setStockMinimo(resultado.getInt("stockMinimo"));
                lista.add(pro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;
    }
    
}
