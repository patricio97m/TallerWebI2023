$(document).ready(function() {
    // Manejar clic en las cartas del jugador
    $(".carta-jugador").click(function() {
        var cartaSeleccionada = $(this).data("carta");
        enviarCartaSeleccionadaAlServidor(cartaSeleccionada);
    });

    // Función para enviar la carta seleccionada al servidor
    function enviarCartaSeleccionadaAlServidor(carta) {
        $.ajax({
            type: "POST",
            url: "/jugar-carta", // Acá va la función del controlador para jugar la carta
            data: {
                carta: carta // Acá van los datos que envía el servidor
            },
            dataType: "json", // Se se espera un json
            success: function(data) {
                // acá se hacen las acciones con los datos que trajo el servidor
            },
            error: function(err) {
                console.error("Error al jugar la carta: " + err);
            }
        });
    }
});