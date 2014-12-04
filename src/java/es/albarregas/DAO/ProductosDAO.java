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
import java.util.HashMap;

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

    public ArrayList<Productos> getProductosSeleccionados(String[] codigosProducto) {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            StringBuffer clausula= new StringBuffer("( '");
            for(int i=0; i<codigosProducto.length;i++){
                clausula.append(codigosProducto[i]+"' , '");
                if(i==codigosProducto.length-1){
                clausula.replace(clausula.lastIndexOf(","), clausula.lastIndexOf(",")+3, ")");
                }
            }
            String query="Select id, precio, denominacion, stock from productos where id in " + clausula.toString();
            
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Productos pro = new Productos();
                pro.setId(resultado.getString("id"));                
                pro.setPrecio(resultado.getDouble("precio"));
                pro.setStock(resultado.getInt("stock"));
                pro.setDenominacion(resultado.getString("denominacion"));                
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
