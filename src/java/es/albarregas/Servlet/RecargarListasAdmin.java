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

import es.albarregas.DAO.ProductosDAO;
import es.albarregas.DAO.UsuariosDAO;
import es.albarregas.Modelo.Productos;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
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
public class RecargarListasAdmin extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        sesion=request.getSession();
        UsuariosDAO usersDAO = new UsuariosDAO();
        ArrayList<Usuarios> lista = usersDAO.getUsuarios();
        sesion.setAttribute("listaUsuarios", lista);
        ProductosDAO proDao = new ProductosDAO();
        ArrayList<Productos> catalogo = new ArrayList<Productos>();
        catalogo = proDao.getListaProductos();
        sesion.setAttribute("catalogo", catalogo);
        getServletContext().getRequestDispatcher("/JSP/Administracion/MenuAdmin.jspx").forward(request, response);
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
