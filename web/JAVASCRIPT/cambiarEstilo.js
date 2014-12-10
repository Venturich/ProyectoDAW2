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


function cambiarEstilo() {

    if (localStorage.getItem("estilo") == null) {
        localStorage.setItem("estilo", "default");
    }
    
    var estilo = localStorage.getItem("estilo");
    if (estilo == "default") {
        localStorage.setItem("estilo", "alternate");
        document.getElementById("default").setAttribute("rel", "desactivada");
        document.getElementById("alternate").setAttribute("rel", "stylesheet");
    } else if (estilo == "alternate") {
        localStorage.setItem("estilo", "default");
        document.getElementById("alternate").setAttribute("rel", "desactivada");
        document.getElementById("default").setAttribute("rel", "stylesheet");

    }
}

function cargarEstiloInicial(){
    if (localStorage.getItem("estilo") == null) {
        localStorage.setItem("estilo", "default");
    }
    
    var estilo = localStorage.getItem("estilo");
    if (estilo == "default") {
        localStorage.setItem("estilo", "alternate");
        document.getElementById("default").setAttribute("rel", "desactivada");
        document.getElementById("alternate").setAttribute("rel", "stylesheet");
    } else if (estilo == "alternate") {
        localStorage.setItem("estilo", "default");
        document.getElementById("alternate").setAttribute("rel", "desactivada");
        document.getElementById("default").setAttribute("rel", "stylesheet");

    }
}


