package practica1.Controladores;

import com.google.gson.Gson;
import io.javalin.Javalin;
import jakarta.persistence.Tuple;
import org.eclipse.jetty.util.ajax.JSON;
import practica1.Clases.Mensaje;
import practica1.Servicios.MensajeServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MensajeriaControlador {
    private Javalin app;
    private MensajeServicio mensajeServicio = MensajeServicio.getInstancia();
    public MensajeriaControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.get("/chat", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            String sender = ctx.sessionAttribute("username");
            modelo.put("clients", mensajeServicio.getClient(sender));
            modelo.put("sender", sender);
            ctx.render("chat.html", modelo);
        });
        app.post("/get/clients", ctx -> {
            ctx.json(mensajeServicio.getClients());
        });
        app.ws("/chat/{accion}", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexión Iniciada - "+ctx.getSessionId());
            });

            ws.onMessage(ctx -> {
                String opc = ctx.pathParam("accion");
                HashMap json = (HashMap) JSON.parse(ctx.message());
                String cliente = json.get("client").toString();
                String sender = json.get("sender").toString();

                switch (opc){
                    case "1":
                        String mensaje = json.get("message").toString();
                        mensajeServicio.creaMensaje(cliente, mensaje, sender);
                        break;
                    case "2":
                        List<Mensaje> mensajes = mensajeServicio.getMessages(sender, cliente);
                        ctx.send(new Gson().toJson(mensajes));
                        break;
                    default:
                        ctx.send("Opcion de ruta no valida");
                        break;
                }
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
            });

            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });
    }
}
