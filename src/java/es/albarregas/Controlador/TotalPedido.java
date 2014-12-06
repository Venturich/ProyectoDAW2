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
package es.albarregas.Controlador;

import es.albarregas.Modelo.Productos;
import java.util.ArrayList;

/**
 *
 * @author Ventura
 */
public class TotalPedido {

    public static double getTotal(ArrayList<Productos> productos) {
        double total = 0.0;
        for (Productos i : productos) {
            total += i.getPrecio();
        }
        return total;

    }

}
