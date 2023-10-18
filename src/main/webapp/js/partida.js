$(document).ready(function() {
    $(".carta-jugador").click(function() {
        var indice = $(this).data("indice-carta");
        var idPartida = obtenerIdPartidaDesdeCookie(); // Implementa esta función para obtener el ID de la partida desde las cookies

        $.ajax({
            type: "POST",
            url: "/enviarJugada",
            data: {
                indice: indice,
                idPartida: idPartida
            },
            dataType: "json",
            success: function(response) {
                if ("error" in response) {
                    console.error("Error al jugar la carta: " + response.error);
                } else {
                    actualizarVista(response.partida); // Implementa esta función para actualizar la vista con los nuevos datos
                }
            },
            error: function(err) {
                console.error("Error al jugar la carta: " + err);
            }
        });
    });
});

function actualizarVista(partida) {
    // Actualiza la vista con los datos de la partida
    // Por ejemplo, actualiza los puntos, cartas jugadas, etc.
    // Asegúrate de modificar la vista según tus necesidades específicas
    $("#puntosJugador").text(partida.puntosJugador);
    $("#puntosIa").text(partida.puntosIa);
    // Actualiza otros elementos de la vista...
}