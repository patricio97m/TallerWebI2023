$(document).ready(function() {
    console.log("JS Cargado");

    $(".carta-jugador").click(function() {
        let tipoJugada = "Carta";
        let indice = parseInt($(this).data("indice-carta"));
        const idPartida = obtenerCookie("idPartida");
        console.log("Jugada: " + tipoJugada + " " + " indice: " + indice + " IDPartida: "+ idPartida);
        enviarJugada(tipoJugada, indice, idPartida);
    });

    $(".btn").click(function() {
        let tipoJugada = $(this).data("tipo-jugada");
        let indice = $(this).data("indice");
        const idPartida = obtenerCookie("idPartida");
        console.log("Jugada: " + tipoJugada + " indice: " + indice + " IDPartida: "+ idPartida);
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
        url: "/spring/enviarJugada",
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
        url: "/spring/recibirCambios",
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

function obtenerCookie(nombreCookie) {0
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith(nombreCookie + '=')) {
            return cookie.substring(nombreCookie.length + 1);
        }
    }
    return null;
}