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
var errorcp = false;
var errordni = false;
var errortelf = false;
function validar() {
    document.getElementById("boton").addEventListener("click", function (event) {
        event.preventDefault();
        var regexDNI = new RegExp("[0-9]{8}[A-Za-z]{1}");
        var nombre = document.getElementsByName("nombre")[0];
        var apellidos = document.getElementsByName("apellidos")[0];
        var nif_nie = document.getElementsByName("nif_nie")[0];
        var direccion = document.getElementsByName("direccion")[0];

        if (!regexDNI.test(nif)) {
            errordni = true;
            document.getElementById("nif_nie").innerHTML = "NIF no valido";
        } else {
            errordni = false;
        }

        if (!errordni && !errorcp && !errortelf) {
            document.getElementById("modificar-datos").submit();
        }


    });


}