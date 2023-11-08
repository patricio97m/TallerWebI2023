$(document).ready(function() {
    console.log("JS Cargado");

    $('#miModal').modal({
        backdrop: 'static',
        keyboard: false
    });

    $('#ultimaJugada').hide();
 
    $(".body").on("click", ".carta-jugador", function(){
        let tipoJugada = "Carta";
        let indice = parseInt($(this).data("indice-carta"));
        const idPartida = obtenerCookie("idPartida");
        console.log("Jugada: " + tipoJugada + " " + " indice: " + indice + " IDPartida: "+ idPartida);
        enviarJugada(tipoJugada, indice, idPartida);
    });

    $(".body").on("click", ".boton-jugada", function(){
        let tipoJugada = $(this).data("tipo-jugada");
        let indice = $(this).data("indice");
        const idPartida = obtenerCookie("idPartida");
        console.log("Jugada: " + tipoJugada + " indice: " + indice + " IDPartida: "+ idPartida);
        enviarJugada(tipoJugada, indice, idPartida);
    });
});

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
            actualizarVista(response, idPartida);
        },
        error: function(err) {
            console.error("Error al jugar la carta: " + err);
            console.log(err.responseText)
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
            console.log(err.responseText)
        }
    });
}

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
    puedeCantarTruco = partida.puedeCantarTruco;
    let envido = partida.envido;
    let envidoAQuerer = partida.envidoAQuerer;
    let cantoEnvido =  partida.cantoEnvido;
    let cantoFaltaEnvido = partida.cantoFaltaEnvido;
    let puntosEnvidoIA = partida.puntosEnvidoIA;
    let puntosEnvidoJugador = partida.puntosEnvidoJugador;
    let ganador = partida.ganador;
    let quienEsMano = partida.quienEsMano;

    //ultimaJugada siempre es undefined por lo que no puedo corroborar si es envido
    //console.log(ultimaJugada);
    // if (ultimaJugada === "Quiero" && no se canto truco)
    if(truco==1 && ultimaJugada=="Quiero"){
        // Muestra puntos de envido solo si la última jugada es "Quiero" y se cantó envido
        // Mostrar temporalmente los puntos de envido del jugador y de la IA
        $('#puntosEnvidoJugador').text('Tu envido: ' + puntosEnvidoJugador).show();
        $('#puntosEnvidoIA').text('Envido IA: ' + puntosEnvidoIA).show();

        // Ocultar los puntos de envido después de cierto tiempo (por ejemplo, 3 segundos)
        setTimeout(function() {
            $('#puntosEnvidoJugador').hide();
            $('#puntosEnvidoIA').hide();
        }, 10000); // 10000 milisegundos (10 segundos)
    }

    actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ganador, truco, envido, cantoEnvido, cantoTruco, puntosEnvidoIA, puntosEnvidoJugador, envidoAQuerer, quienEsMano);
    console.log("Canto = " + ultimaJugada);
    actualizarCartas(manoDelJugador, cartasRestantesIa, cartasJugadasIa, cartasJugadasJugador);
    actualizarBotones(puedeCantarTruco);



    if(turnoIA)setTimeout(function() {
        recibirCambios(idPartida);
    }, 2000); // 2000 milisegundos (2 segundos)


}


function obtenerCookie(nombreCookie) {0
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(nombreCookie + '=')) {
            return cookie.substring(nombreCookie.length + 1);
        }
    }
    return null;
}

function actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ganador, truco, envido, cantoEnvido, cantoTruco, puntosEnvidoIA, puntosEnvidoJugador, envidoAQuerer, quienEsMano) {
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
    const volverAlMenuButton = $('#miModal #volverAlMenu');
    const popover = $('#ultimaJugada');
    const popoverBody = $('.popover-body');


    if (ultimaJugada === 'Quiero' && turnoIA === false ) {
        popover.show();
        popoverBody.text("IA: " + ultimaJugada);

        setTimeout(function() {
            popover.hide();
        }, 3000);
    } else if (ultimaJugada === 'No Quiero' && turnoIA === false) {
        popover.show();
        popoverBody.text("IA: " + ultimaJugada);

        setTimeout(function() {
            popover.hide();
        }, 3000);
    } else {
        popover.hide();
    }

    puntosJugadorElement.text(puntosJugador + ' Puntos');
    puntosIaElement.text(puntosIa + ' Puntos');

    if (turnoIA == false) { //Acá habría que arreglar para que salte el modal
        if(cantoEnvido){
                //Acá haría falta verificar si se cantó envido primero
            if (envidoAQuerer == 2){// aca se muestran los botones quiero, no quiero, envido, real envido y falta envido
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta envido" );
                quieroEnvidoButton.show();noQuieroEnvidoButton.show();envidoButton.show();realEnvidoButton.show();faltaEnvidoButton.show();
                quieroTrucoButton.hide();noQuieroTrucoButton.hide(); quieroRetrucoButton.hide();quieroValeCuatroButton.hide()
                volverAlMenuButton.hide();quieroValeCuatroButton.hide();
            }
            else if (envidoAQuerer == 3){ // aca se muestran los botones quiero, no quiero y falta envido
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta real envido" );
                quieroEnvidoButton.show();noQuieroEnvidoButton.show();faltaEnvidoButton.show();
                quieroTrucoButton.hide();noQuieroTrucoButton.hide();quieroRetrucoButton.hide();quieroValeCuatroButton.hide()
                volverAlMenuButton.hide();quieroValeCuatroButton.hide();
                envidoButton.hide(); realEnvidoButton.hide();
            }
            else if (envidoAQuerer > 3){ // aca se muestran los botones quiero y no quiero
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta falta envido" );
                quieroEnvidoButton.show();noQuieroEnvidoButton.show();
                quieroTrucoButton.hide();noQuieroTrucoButton.hide();quieroRetrucoButton.hide();quieroValeCuatroButton.hide()
                volverAlMenuButton.hide();quieroValeCuatroButton.hide();envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
            }
        }
        if(cantoTruco){
            if (truco == 1 ){// se muestra quiero, no quiero y retruco
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta truco" );
                quieroTrucoButton.show();noQuieroTrucoButton.show();quieroRetrucoButton.show();
                volverAlMenuButton.hide();quieroValeCuatroButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
                envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
            }
            else if (truco == 2){ // se muestra quiero, no quiero y vale cuatro
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta retruco" );
                quieroTrucoButton.show();noQuieroTrucoButton.show();quieroValeCuatroButton.show();
                volverAlMenuButton.hide();quieroRetrucoButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
                envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
            }
            else if (truco == 3){ // se muestra quiero, no quiero
                $('#miModal').modal('show');
                $('#miModal .modal-body h5').text("La IA canta vale cuatro" );
                quieroTrucoButton.show();noQuieroTrucoButton.show();
                volverAlMenuButton.hide();quieroRetrucoButton.hide();quieroValeCuatroButton.hide();quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();
                envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
            }
        }

        
    } else {
        // Si turnoIA es falso, oculta el modal
        $('#miModal').modal('hide');
    }

    if (ganador != "null") {
        // Si hay un ganador, muestra el mensaje del ganador en el modal
        $('#miModal').modal('show');
        $('#miModal .modal-body h5').text("El ganador es " + ganador);
        volverAlMenuButton.show();
        quieroTrucoButton.hide();noQuieroTrucoButton.hide();quieroRetrucoButton.hide();quieroValeCuatroButton.hide()
        quieroEnvidoButton.hide();noQuieroEnvidoButton.hide();envidoButton.hide(); realEnvidoButton.hide(); faltaEnvidoButton.hide();
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
    for (i = 0; i < cartasJugadasIa.length; i++) {
        if(cartasJugadasIa[i] != "NULL"){
            cartasIA.append('<div class="col-4 carta"><img src="/spring/img/cards/' + cartasJugadasIa[i] + '.png" class="img-fluid"></div>');
        }
        else{
            cartasIA.append('<div class="col-4 carta"><img src="/spring/img/cards/lugar-vacio.png "class="img-fluid"></div>');
        }
        
    }

    // Actualiza las cartas jugadas por el jugador
    var cartasJugador = $(".cartasJugadasJugador");
    cartasJugador.empty(); // Limpia las cartas anteriores
    for (i = 0; i < cartasJugadasJugador.length; i++) {
        if(cartasJugadasJugador[i] != "NULL"){
            cartasJugador.append('<div class="col-4 carta"><img src="/spring/img/cards/' + cartasJugadasJugador[i] + '.png" class="img-fluid"></div>');
        }
        else{
            cartasJugador.append('<div class="col-4 carta"><img src="/spring/img/cards/lugar-vacio.png "class="img-fluid"></div>');
        }    
    }

    // Actualiza las cartas del jugador
    var cartasJugadorPropias = $(".cartasDelJugador");
    cartasJugadorPropias.empty(); // Limpia las cartas anteriores
    for (i = 0; i < manoDelJugador.length; i++) {
        if(manoDelJugador[i] != "NULL"){
            cartasJugadorPropias.append('<div class="col-4 carta"><img src="/spring/img/cards/' + manoDelJugador[i] + '.png" class="img-fluid shake carta-jugador" data-indice-carta="' + (i + 1) + '"></div>');
        }
        else{
            cartasJugadorPropias.append('<div class="col-4 carta"><img src="/spring/img/cards/lugar-vacio.png "class="img-fluid"></div>');
        }
    }
}

function actualizarBotones(puedeCantarTruco){
    //const botonTruco = document.getElementById("Truco");
    //if(puedeCantarTruco){
    //    botonTruco.style.display = 'block'
    //}
    //else{
    //    botonTruco.style.display = 'hidden'
    //}
}