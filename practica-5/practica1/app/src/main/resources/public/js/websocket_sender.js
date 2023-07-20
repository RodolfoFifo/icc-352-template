var webSocket;
var tiempoReconectar = 5000;

$(document).ready(function() {
    connectWebSocketSender();
})

function validateChatName() {
    let tmp_sender = $('#name_field').val();

    if(tmp_sender.trim() != '' && tmp_sender.trim() != "Support"){
        sender = $('#name_field').val();
        $('#name_field').prop("disabled", true );
        $('#submit_button').prop("disabled", true );
        $('#input_message').prop("disabled", false );
        $('#send_button').prop("disabled", false );
        sendRequest();
    } else {
        alert('Nombre de usuario no valido');
    }
}

function connectWebSocketSender() {
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/1");
    webSocket.onopen  = function(e){ console.log("Conectado Sender - status "+this.readyState); };
    webSocket.onclose = function(e){console.log("Desconectado Sender - status "+this.readyState);};
}

function sendMessage() {
    let message = $('#input_message').val();
    console.log(client);

    if(message != '') {
        var obj = new Object();
        obj.client = client;
        obj.message = message;
        obj.sender = sender
        $('#input_message').val('');
        $('#message_chat').append(`<div class="rounded bg-primary text-white d-flex justify-content-end mb-1 py-2 px-3">${message}</div>`)
        webSocket.send(JSON.stringify(obj));
    }
}
