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
package es.albarregas.Servlet;

import es.albarregas.DAO.LineasPedidosDAO;
import es.albarregas.DAO.PedidosDAO;
import es.albarregas.DAO.ProductosDAO;
import es.albarregas.Modelo.Clientes;
import es.albarregas.Modelo.Productos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ventura
 */
public class ProcesarCompra extends HttpServlet {

    private HttpSession sesion;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        sesion = request.getSession();
        if (request.getParameter("boton").equals("volver")) {
            getServletContext().getRequestDispatcher("/JSP/Usuario/MenuUsuario.jspx").forward(request, response);
        } else if (request.getParameter("boton").equals("comprar")) {
            int idPedido;
            int idCliente = ((Clientes) sesion.getAttribute("cliente")).getId();
            PedidosDAO pedDao = new PedidosDAO();
            idPedido = pedDao.setNuevoPedido(idCliente);

            String[] codigosProducto = request.getParameterValues("idProducto");

            LineasPedidosDAO lpDao = new LineasPedidosDAO();
            lpDao.addLineasPedido(codigosProducto, idPedido);

            getServletContext().getRequestDispatcher("/JSP/Compra/AgregadoACarrito.jspx").forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
