var webSocketListener;
var wait_time = 5000;

$(document).ready(function() {
    connectWebSocketListener();
    setInterval(sendRequest, wait_time);
});

function connectWebSocketListener() {
    webSocketListener = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/2");
    webSocketListener.onmessage = function(data){getResponse(data);};
    webSocketListener.onopen  = function(e){ console.log("Conectado Listener - status "+this.readyState); };
    webSocketListener.onclose = function(e){console.log("Desconectado Listener - status "+this.readyState); };
}

function sendRequest() {
    if(sender && client && !$('#input_message').attr("disabled")){
        var obj = new Object();
        obj.client = client;
        obj.sender = sender

        webSocketListener.send(JSON.stringify(obj));
    }
}

function getResponse(json){
    let list_msg = JSON.parse(json['data']);
    let html = '';

    list_msg.forEach(element => {

        if(sender == element['sender']) {
           html += `<div class="rounded bg-primary text-white d-flex justify-content-end mb-1 py-2 px-3">${element['mensaje']}</div>`
        }
        else {
            html += `<div class="rounded bg-light border  mb-1 py-2 px-3">${element['mensaje']}</div>`
        }
    });

    $('#message_chat').html(html);
}




