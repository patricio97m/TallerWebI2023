$(document).ready(function() {
    console.log("JS Cargado");

    $(".carta-jugador").click(function() {
        let tipoJugada = "Carta";
        let indice = $(this).data("indice-carta");
        let idPartida = obtenerIdPartidaDesdeCookie(); // Implementa esta función para obtener el ID de la partida desde las cookies
        console.log("Jugada= " + tipoJugada + " " + indice);
        enviarJugada(tipoJugada, indice, idPartida);
        
    });
});

function actualizarVista(partida) {
    // Ya se debería de poder leer todos los datos que venga en el metodo getDetallesPartida()
    $("#puntosJugador").text(partida.puntosJugador);
    $("#puntosIa").text(partida.puntosIa);
}

function enviarJugada(tipoJugada, indice, idPartida){
    $.ajax({
        type: "POST",
        url: "/enviarJugada",
        data: {
            tipoJugada: tipoJugada,
            indice: indice,
            idPartida: idPartida
        },
        dataType: "json",
        success: function(response) {
            if ("error" in response) {
                console.error("Error al jugar la carta: " + response.error);
            } else {
                actualizarVista(response); // Implementa esta función para actualizar la vista con los nuevos datos
                setTimeout(function() {
                    recibirCambios();
                }, 2000); // 2000 milisegundos (2 segundos)
            }
        },
        error: function(err) {
            console.error("Error al jugar la carta: " + err);
        }
    });
}

function recibirCambios() {
    $.ajax({
        type: "POST",
        url: "/recibirCambios",
        dataType: "json",
        success: function(response) {
            actualizarVista(response);
            // Procesa la respuesta de la nueva solicitud aquí
            // ...
        },
        error: function(err) {
            console.error("Error al recibir cambios: " + err);
        }
    });
}

function obtenerCookie(nombre) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith(nombre + '=')) {
            return cookie.substring(nombre.length + 1);
        }
    }
    return null;
}