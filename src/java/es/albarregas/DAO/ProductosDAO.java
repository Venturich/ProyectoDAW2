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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            StringBuffer clausula = new StringBuffer("( '");
            for (int i = 0; i < codigosProducto.length; i++) {
                clausula.append(codigosProducto[i] + "' , '");
                if (i == codigosProducto.length - 1) {
                    clausula.replace(clausula.lastIndexOf(","), clausula.lastIndexOf(",") + 3, ")");
                }
            }
            String query = "Select * from productos where id in " + clausula.toString();
            System.out.println(query);
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Productos pro = new Productos();
                pro.setId(resultado.getString("id"));
                pro.setPrecio(resultado.getDouble("precio"));
                pro.setStock(resultado.getInt("stock"));
                pro.setStockMinimo(resultado.getInt("stockMinimo"));
                pro.setDenominacion(resultado.getString("denominacion"));
                pro.setDescripcion(resultado.getString("descripcion"));
                lista.add(pro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;

    }

    public String updateStock(ArrayList<Productos> carrito) {
        String query = "update productos set stock=stock-1 where stock >0 and id=?";
        String estado = "x";
        int contador = 0;
        try {
            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            conexion.setAutoCommit(false);

            for (Productos i : carrito) {
                System.out.println("producto: " + i.getId());
                sentencia.setString(1, i.getId());
                sentencia.addBatch();
            }
            int[] lineas = sentencia.executeBatch();
            conexion.commit();
            conexion.setAutoCommit(true);
            for (int i = 0; i < lineas.length; i++) {
                if (lineas[i] > 0) {
                    contador += lineas[i];
                    System.out.println("filas actualizadas " + lineas[i]);
                }
            }
            if (contador < carrito.size()) {
                estado = "p";
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return estado;
    }

    public ArrayList<Productos> getProductosDePedido(int idPedido) {

        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {

            iniciarConexion();
            sentencia = conexion.prepareStatement("select * from productos where id in (select codigoProducto from lineasPedidos where numeroPedido=?)");
            sentencia.setInt(1, idPedido);
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

    public ArrayList<Productos> getListaProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {

            iniciarConexion();
            sentencia = conexion.prepareStatement("select * from productos ");
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Productos pro = new Productos();
                pro.setId(resultado.getString("id"));
                pro.setDenominacion(resultado.getString("denominacion"));
                pro.setDescripcion(resultado.getString("descripcion"));
                pro.setPrecio(resultado.getDouble("precio"));
                pro.setStock(resultado.getInt("stock"));
                pro.setStockMinimo(resultado.getInt("stockMinimo"));
                pro.setBloqueado(resultado.getString("bloqueado"));
                lista.add(pro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }

        return lista;
    }

    public int updateEstados(ArrayList<Productos> modificar) {

        String query = "update productos set bloqueado = ? where id = ?";
        LineasPedidosDAO lpDao = new LineasPedidosDAO();
        List<String> lista = lpDao.getProductosEnPedidosPendientes(modificar);
        int total = -1;
        try {

            iniciarConexion();
            sentencia = conexion.prepareStatement(query);
            conexion.setAutoCommit(false);

            for (Productos i : modificar) {

                if (!lista.contains(i.getId())) {
                    System.out.println("ProductoDAO: " + i.getId() + " - " + i.getBloqueado());

                    sentencia.setString(1, i.getBloqueado());
                    sentencia.setString(2, i.getId());
                    sentencia.addBatch();
                }
            }
            total = sentencia.executeBatch().length;
            conexion.commit();
            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexion();
        }
        return (total);

    }

}
