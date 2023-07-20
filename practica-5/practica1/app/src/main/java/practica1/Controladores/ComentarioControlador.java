package practica1.Controladores;

import io.javalin.Javalin;
import org.jasypt.util.text.BasicTextEncryptor;
import practica1.Servicios.ComentarioServicio;

public class ComentarioControlador {
    private Javalin app;
    private ComentarioServicio comentarioServicio = ComentarioServicio.getInstancia();

    public ComentarioControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.post("/comentarios/{id}", ctx -> {
            String body = ctx.formParam("comentario");
            String autor = ctx.sessionAttribute("username");
            String id = ctx.pathParam("id");

            comentarioServicio.crearComentario(Long.parseLong(id), body, autor);

            ctx.redirect("/articulo/" +  id);
        });
    }
}
