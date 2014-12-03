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

import es.albarregas.Modelo.Clientes;
import es.albarregas.Modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

/**
 *
 * @author Ventura
 */
public class ComprobarRegistro extends HttpServlet {

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
        String campoErroneo = new String();
        Boolean error = false;
        response.setContentType("text/html;charset=UTF-8");
        sesion = request.getSession(true);
        Enumeration<String> nombres = request.getAttributeNames();
        while (nombres.hasMoreElements()) {
            String nombre = nombres.nextElement();
            if (request.getParameter(nombre).equals("")) {
                error = true;
                campoErroneo = nombre;
                break;
            }

        }
        if (!request.getParameter("clave").equals(request.getParameter("confClave"))) {
            error = true;
            campoErroneo ="confClave";
        }
        if (error) {
            sesion.setAttribute("campoErroneo", campoErroneo);
            getServletContext().getRequestDispatcher("/JSP/Registro/registro.jsp").forward(request, response);
        } else {

            try {

                Usuarios usuario = new Usuarios();
                Clientes cliente = new Clientes();

                /* convertimos la fecha*/
                DateConverter converter = new DateConverter();
                converter.setPattern("yyyy-mm-dd");
                ConvertUtils.register(converter, Date.class);
                BeanUtils.populate(cliente, request.getParameterMap());
                /*
                BeanUtils.setProperty(cliente, "fechaNacimiento", request.getParameter("fechaNacimiento"));
                BeanUtils.setProperty(cliente, "nombre", request.getParameter("nombre"));
                BeanUtils.setProperty(cliente, "apellidos", request.getParameter("apellidos"));
                BeanUtils.setProperty(cliente, "nif_nie", request.getParameter("nif_nie"));
                BeanUtils.setProperty(cliente, "sexo", request.getParameter("sexo"));
                BeanUtils.setProperty(cliente, "direccion", request.getParameter("direccion"));*/
                BeanUtils.populate(usuario, request.getParameterMap());

                sesion.setAttribute("usuario", usuario);
                sesion.setAttribute("cliente", cliente);

                getServletContext().getRequestDispatcher("/ProcesarRegistro").forward(request, response);

            } catch (IllegalAccessException ex) {
                Logger.getLogger(ComprobarRegistro.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ComprobarRegistro.class.getName()).log(Level.SEVERE, null, ex);
            }

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
