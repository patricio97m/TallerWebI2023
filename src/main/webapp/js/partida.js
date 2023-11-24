$(document).ready(function() {
    console.log("JS Cargado");

    animarCartas();


    $('#miModal').modal({
        backdrop: 'static',
        keyboard: false
    });

    $(".ocultar-boton").hide();

    $("#mostrar-ayudas").click(function() {
        $(".ocultar-boton").toggle();
    });

    $('#ultimaJugada').hide();

    // Oculta los botones de tipo de envido
    $(".btn-tipos-envido").hide();

    // Maneja el clic en el botón mostrarBotonesEnvido
    $("#mostrarBotonesEnvido").on("click", function() {
        $(".btn-tipos-envido").toggle();
    });

    $("#repartir-ayuda").on("click", function() {
        $('#modalAyudas .modal-header h5').text("Repartir cartas" );
        $('#modalAyudas .modal-body p').text("Esta habilidad te permite volver a repartir las cartas" );
        $('#modalAyudas .modal-footer .boton-jugada').data("indice", 1).attr("data-indice", 1);
        $('#btn-comprarAyuda').attr("href", "http://localhost:8080/spring/realizarCompra?potenciadorElegido=1");
    });
    $("#intercambiar-ayuda").on("click", function() {
        $('#modalAyudas .modal-header h5').text("Intercambiar cartas" );
        $('#modalAyudas .modal-body p').text("Esta habilidad te permite intercambiar tus cartas con las del rival" );
        $('#modalAyudas .modal-footer .boton-jugada').data("indice", 2).attr("data-indice", 2);   
        $('#btn-comprarAyuda').attr("href", "http://localhost:8080/spring/realizarCompra?potenciadorElegido=2");
    });
    $("#3-puntos-ayuda").on("click", function() {
        $('#modalAyudas .modal-header h5').text("3 puntos");
        $('#modalAyudas .modal-body p').text("Esta habilidad te permite sumarte 3 puntos instantaneamente" );
        $('#modalAyudas .modal-footer .boton-jugada').data("indice", 3).attr("data-indice", 3);
        $('#btn-comprarAyuda').attr("href", "http://localhost:8080/spring/realizarCompra?potenciadorElegido=3");
    });

    $('#btn-comprarAyuda').on("click", function(){
        window.location.href = $('#btn-comprarAyuda').attr("href")
    })
    


    $(".body").on("click", ".carta-jugador", function(){
        let tipoJugada = "Carta";
        let indice = parseInt($(this).data("indice-carta"));
        console.log("Jugada: " + tipoJugada + " " + " indice: " + indice);
        enviarJugada(tipoJugada, indice);
    });

    $(".body").on("click", ".boton-jugada", function(){
        let tipoJugada = $(this).data("tipo-jugada");
        let indice = $(this).data("indice");
        console.log("Indice: ") + $(this).data("indice")
        console.log("Jugada: " + tipoJugada + " indice: " + indice);
        enviarJugada(tipoJugada, indice);
    });


});

function enviarJugada(tipoJugada, indice){
    $.ajax({
        type: "POST",
        url: "/spring/enviarJugada",
        data: {
            tipoJugada: tipoJugada,
            indice: indice
        },
        dataType: "json",
        success: function(response) {
            if (response.redirect) {
                // Redirige al usuario a la URL proporcionada en el objeto JSON
                window.location.href = response.redirect;
            } else {
                actualizarVista(response);
            }
            
        },
        error: function(err) {
            console.error("Error al jugar la carta: " + err);
            console.log(err.responseText)
        }
    });
}

function recibirCambios() {
    $.ajax({
        type: "POST",
        url: "/spring/recibirCambios",
        data:{
        },
        dataType: "json",
        success: function(response) {
            actualizarVista(response);
        },
        error: function(err) {
            console.error("Error al recibir cambios: " + err);
            console.log(err.responseText)
        }
    });
}

function actualizarVista(partida) {

    console.log(partida)
    let seRepartieronCartas = partida.seRepartieronCartas;
    if(seRepartieronCartas){
        //ANIMACION DE REPARTIR LAS CARTAS
        console.log("Se Repartieron Cartas")
        animarCartas();
    }
    let ultimaJugada = partida.ultimaJugada;
    let ultimoJugador = partida.ultimoJugador;
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
    let puntosEnvidoIA = partida.puntosEnvidoIA;
    let puntosEnvidoJugador = partida.puntosEnvidoJugador;
    let ganador = partida.ganador;
    let tiradaActual = partida.tiradaActual;
    let puedeCantarTruco = partida.puedeCantarTruco;
    let ayudasRepartirCartas = partida.ayudasRepartirCartas;
    let ayudasIntercambiarCartas = partida.ayudasIntercambiarCartas;
    let ayudasSumarPuntos = partida.ayudasSumarPuntos;

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

    actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ultimoJugador, ganador, truco, envido, cantoEnvido, cantoTruco, puntosEnvidoIA, puntosEnvidoJugador, envidoAQuerer, tiradaActual, puedeCantarTruco);
    console.log("Canto = " + ultimaJugada);
    actualizarCartas(manoDelJugador, cartasRestantesIa, cartasJugadasIa, cartasJugadasJugador);
    actualizarBotones(puedeCantarTruco);
    actualizarAyudas(ayudasRepartirCartas, ayudasIntercambiarCartas, ayudasSumarPuntos);

    if(turnoIA)setTimeout(function() {
        recibirCambios();
    }, 1500); // (1.5 segundos)
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

function actualizarDatos(puntosJugador, puntosIa, turnoIA, ultimaJugada, ultimoJugador, ganador, truco, envido, cantoEnvido, cantoTruco, puntosEnvidoIA, puntosEnvidoJugador, envidoAQuerer, tiradaActual, puedeCantarTruco) {
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
    const trucoButtonInferior = $('#Truco');
    const mostrarBotonesEnvido = $('#mostrarBotonesEnvido');


    if (ultimaJugada === 'Quiero' && ultimoJugador === 'IA') {
        popover.show();
        popoverBody.text("IA: QUIERO");

        setTimeout(function() {
            popover.hide();
        }, 3000);
    } else if (ultimaJugada === 'No Quiero' && ultimoJugador === 'IA') {
        popover.show();
        popoverBody.text("IA: NO QUIERO");

        setTimeout(function() {
            popover.hide();
        }, 3000);
    } else {
        popover.hide();
    }

    if (tiradaActual > 1 || envido < 0){
        mostrarBotonesEnvido.prop('disabled', true);
        $(".btn-tipos-envido").hide();
    }else {
        mostrarBotonesEnvido.prop('disabled', false);
    }

    if (!puedeCantarTruco){
        trucoButtonInferior.prop('disabled', true);
        $(".btn-tipos-envido").hide();
    }else trucoButtonInferior.prop('disabled', false);

    if (truco === 2 && puedeCantarTruco){
        trucoButtonInferior.text("Retruco");
    } else if (truco === 3 && puedeCantarTruco){
        trucoButtonInferior.text("Vale cuatro");
    }else if(truco === 4) {
        trucoButtonInferior.text("Vale cuatro");
    }
    else trucoButtonInferior.text("Truco");

    puntosJugadorElement.text(puntosJugador + ' Puntos');
    puntosIaElement.text(puntosIa + ' Puntos');

    if (turnoIA == false) {
        if(cantoEnvido){
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


function animarCartas() {

    $('#repartiendoCartasModal').modal('show');

    setTimeout(function () {
        // Oculta el modal después de 3 segundos (ajusta según sea necesario)
        $('#repartiendoCartasModal').modal('hide');
        $('#puntosEnvidoJugador').hide();
        $('#puntosEnvidoIA').hide();
    }, 3000); // Cambié el tiempo a 3000 milisegundos (3 segundos)

    setTimeout(function (){
    // Obtén todas las cartas del jugador
    var cartas = document.querySelectorAll('.cartasDelJugador');
    var cartasReverso = document.querySelectorAll('.cartasDelJugadorReverso');

    // Remueve las clases existentes para permitir que la animación se repita
    cartasReverso.forEach(function (cartasReverso) {
        cartasReverso.classList.remove('mover');
        cartasReverso.classList.remove('displayNone');
    });

    cartas.forEach(function (carta) {
        carta.classList.remove('displayBlock');
    });

    // Agrega la clase de animación a cada carta
    cartasReverso.forEach(function (cartasReverso) {
        cartasReverso.classList.add('mover');
    });

    setTimeout(function () {
        cartas.forEach(function (carta) {
            carta.classList.add('displayBlock');
        });
    }, 1000);

    setTimeout(function () {
        cartasReverso.forEach(function (cartasReverso) {
            cartasReverso.classList.add('displayNone');
        });
    }, 2000);
    }, 1000);
}

function actualizarAyudas(ayudasRepartirCartas, ayudasIntercambiarCartas, ayudasSumarPuntos){
    $("#repartir-ayuda span").text(ayudasRepartirCartas);
    $("#intercambiar-ayuda span").text(ayudasIntercambiarCartas);
    $("#3-puntos-ayuda span").text(ayudasSumarPuntos);

}