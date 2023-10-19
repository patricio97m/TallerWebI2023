$(document).ready(function() {
    console.log("JS Cargado");

    let puedeJugarCarta = true;
 
    $(".body").on("click", ".carta-jugador", function(){
        if(puedeJugarCarta){
            let tipoJugada = "Carta";
            let indice = parseInt($(this).data("indice-carta"));
            const idPartida = obtenerCookie("idPartida");
            console.log("Jugada: " + tipoJugada + " " + " indice: " + indice + " IDPartida: "+ idPartida);
            enviarJugada(tipoJugada, indice, idPartida);
        }
    });

    $(".body").on("click", ".boton-jugada", function(){
        let tipoJugada = $(this).data("tipo-jugada");
        let indice = $(this).data("indice");
        const idPartida = obtenerCookie("idPartida");
        console.log("Jugada: " + tipoJugada + " indice: " + indice + " IDPartida: "+ idPartida);
        enviarJugada(tipoJugada, indice, idPartida);
    });
});

function actualizarVista(partida, idPartida) {
    console.log(partida)
    // Ya se debería de poder leer todos los datos que venga en el metodo getDetallesPartida()
    let ultimaJugada = partida.ultimaJugada;
    let turnoIA = partida.turnoIA;
    let manoDelJugador = partida.manoDelJugador;
    let cartasRestantesIa = partida.cartasRestantesIa;
    let cartasJugadasIa = partida.cartasJugadasIa;
    let cartasJugadasJugador = partida.cartasJugadasJugador;
    let puntosJugador = partida.puntosJugador;
    let puntosIa = partida.puntosIa;
    let truco = partida.truco;
    let trucoAQuerer = partida.trucoAQuerer;
    let cantoTruco = partida.cantoTruco;
    let envido = partida.envido;
    let envidoAQuerer = partida.envidoAQuerer;
    let cantoEnvido =  partida.cantoEnvido;
    let cantoFaltaEnvido = partida.cantoFaltaEnvido;
    let ganador = partida.ganador;

    puedeJugarCarta = !(cantoEnvido || cantoTruco);

    actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ganador, truco, envido);
    console.log("Canto = " + ultimaJugada);
    actualizarCartas(manoDelJugador, cartasRestantesIa, cartasJugadasIa, cartasJugadasJugador);

    if(turnoIA)setTimeout(function() {
        recibirCambios(idPartida);
    }, 2000); // 2000 milisegundos (2 segundos)
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
                actualizarVista(response, idPartida); // Implementa esta función para actualizar la vista con los nuevos datos
            }
        },
        error: function(err) {
            console.error("Error al jugar la carta: " + err);
        }
    });
}

function recibirCambios(idPartida) {
    $.ajax({
        type: "POST",
        url: "/spring/recibirCambios",
        data:{
            idPartida: idPartida
        },
        dataType: "json",
        success: function(response) {
            actualizarVista(response, idPartida);
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

function actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ganador, truco, envido) {
    const puntosJugadorElement = $('#puntosJugador');
    const puntosIaElement = $('#puntosIa');
    // Así se deberían llamar los botones para hacer la lógica de quiero y no quiero
    const quieroTrucoButton = $('#miModal #quieroTruco');
    const noQuieroTrucoButton = $('#miModal #noQuieroTruco');
    const quieroRetrucoButton = $('#miModal #retruco');
    const quieroValeCuatroButton = $('#miModal #valeCuatro');
    const quieroEnvidoButton = $('#miModal #quieroEnvido');
    const noQuieroEnvidoButton = $('#miModal #noQuieroEnvido');
    const envidoButton = $('#miModal #envido');
    const realEnvidoButton = $('#miModal #realEnvido');
    const faltaEnvidoButton = $('#miModal #faltaEnvido');
    const volverAlMenuButton = $('#miModal .btn-secondary');

    puntosJugadorElement.text(puntosJugador + ' Puntos');
    puntosIaElement.text(puntosIa + ' Puntos');

    if (turnoIA && ultimaJugada !== "null") { //Acá habría que arreglar para que salte el modal

        // aca faltaría verificar que la ia cantó truco
        if (truco === 2){// se muestra quiero, no quiero y retruco
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta truco" );
            volverAlMenuButton.hide();quieroValeCuatroButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
            envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
        }
        else if (truco === 3){ // se muestra quiero, no quiero y vale cuatro
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta retruco" );
            volverAlMenuButton.hide();quieroRetrucoButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
            envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
        }
        else if (truco === 4){ // se muestra quiero, no quiero
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta vale cuatro" );
            volverAlMenuButton.hide();quieroRetrucoButton.hide();quieroValeCuatroButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
            envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
        }

        //Acá haría falta verificar si se cantó envido primero
        if (envido === 2){// aca se muestran los botones quiero, no quiero, envido, real envido y falta envido
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta envido" );
            quieroTrucoButton.hide();noQuieroTrucoButton.hide(); quieroRetrucoButton.hide();
            volverAlMenuButton.hide();quieroValeCuatroButton.hide();
        }
        else if (envido === 3){ // aca se muestran los botones quiero, no quiero y falta envido
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta real envido" );
            quieroTrucoButton.hide();noQuieroTrucoButton.hide();quieroRetrucoButton.hide();
            volverAlMenuButton.hide();quieroValeCuatroButton.hide();
            envidoButton.hide(); realEnvidoButton.hide();
        }
        else if (envido === 4){ // aca se muestran los botones quiero y no quiero
            $('#miModal').modal('show');
            $('#miModal .modal-body h5').text("La IA canta falta envido" );
            quieroTrucoButton.hide();noQuieroTrucoButton.hide();quieroRetrucoButton.hide();
            volverAlMenuButton.hide();quieroValeCuatroButton.hide();envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
        }
    } else {
        // Si turnoIA es falso, oculta el modal
        $('#miModal').modal('hide');
    }

    if (ganador !== "null") {
        // Si hay un ganador, muestra el mensaje del ganador en el modal
        $('#miModal').modal('show');
        $('#miModal .modal-body h5').text("El ganador es " + ganador);
        quieroButton.hide();
        noQuieroButton.hide();
    }
}
function actualizarCartas(manoDelJugador, cartasRestantesIa, cartasJugadasIa, cartasJugadasJugador) {
    // Actualiza las cartas del oponente
    var cartasOponente = $(".cartasDeLaIA");
    cartasOponente.empty(); // Limpia las cartas anteriores
    for (var i = 0; i < cartasRestantesIa; i++) {
        cartasOponente.append('<div class="col-4 carta"><img src="/spring/img/cards/reverso.png" class="img-fluid"></div>');
    }

    // Actualiza las cartas jugadas por la IA
    var cartasIA = $(".cartasJugadasIA");
    cartasIA.empty(); // Limpia las cartas anteriores
    for (var i = 0; i < cartasJugadasIa.length; i++) {
        cartasIA.append('<div class="col-4 carta"><img src="/spring/img/cards/' + cartasJugadasIa[i] + '.png" class="img-fluid"></div>');
    }

    // Actualiza las cartas jugadas por el jugador
    var cartasJugador = $(".cartasJugadasJugador");
    cartasJugador.empty(); // Limpia las cartas anteriores
    for (var i = 0; i < cartasJugadasJugador.length; i++) {
        cartasJugador.append('<div class="col-4 carta"><img src="/spring/img/cards/' + cartasJugadasJugador[i] + '.png" class="img-fluid"></div>');
    }

    // Actualiza las cartas del jugador
    var cartasJugadorPropias = $(".cartasDelJugador");
    cartasJugadorPropias.empty(); // Limpia las cartas anteriores
    for (var i = 0; i < manoDelJugador.length; i++) {
        cartasJugadorPropias.append('<div class="col-4 carta"><img src="/spring/img/cards/' + manoDelJugador[i] + '.png" class="img-fluid shake carta-jugador" data-indice-carta="' + (i + 1) + '"></div>');
    }

    function actualizarBotones(){

    };
}