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

import es.albarregas.Controlador.TotalPedido;
import es.albarregas.DAO.PedidosDAO;
import es.albarregas.DAO.ProductosDAO;
import es.albarregas.Modelo.Pedidos;
import es.albarregas.Modelo.Productos;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ventura
 */
public class FinalizarCompra extends HttpServlet {

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
        PedidosDAO pedDao;
        ProductosDAO proDao;
        if (request.getParameter("boton").equals("finalizar")) {
            ArrayList<Pedidos> pendiente = ((ArrayList<Pedidos>) sesion.getAttribute("pedidosPendientes"));
            int idPedido = pendiente.get(0).getIdPedido();
            double total = TotalPedido.getTotal(((ArrayList<Productos>) sesion.getAttribute("carrito")));
            proDao = new ProductosDAO();    
            
            String estado=proDao.updateStock(((ArrayList<Productos>) sesion.getAttribute("carrito")));  
            pedDao = new PedidosDAO();
            pedDao.finalizarPedido(idPedido, total, estado);
            
            pendiente.clear();
            sesion.setAttribute("pedidosPendientes", pendiente);
            ArrayList<Productos> carrito = ((ArrayList<Productos>) sesion.getAttribute("carrito"));
            carrito.clear();
            sesion.setAttribute("carrito", carrito);
            pedDao = new PedidosDAO();
            Usuarios user = (Usuarios)sesion.getAttribute("usuario");
            ArrayList<Pedidos> finalizados = pedDao.getPedidosFinalizados(user.getId());
            sesion.setAttribute("pedidosFinalizados", finalizados);

        } else {
            getServletContext().getRequestDispatcher("/JSP/Usuario/MenuUsuario.jspx").forward(request, response);
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
