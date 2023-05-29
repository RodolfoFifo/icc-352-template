package practica1.Controladores;

import io.javalin.Javalin;
import practica1.Clases.Usuario;
import practica1.Servicios.ArticuloServicio;
import practica1.Servicios.ComentarioServicio;
import practica1.Servicios.UsuarioServicio;

public class ComentarioControlador {
    private Javalin app;
    private ComentarioServicio comentarioServicio = ComentarioServicio.getInstancia();

    public ComentarioControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.post("/comentarios/{id}", ctx -> {
            String body = ctx.formParam("comentario");
            String autor =  ctx.sessionAttribute("username");
            String id = ctx.pathParam("id");

            comentarioServicio.crearComentario(Long.parseLong(id), body, autor);

            ctx.redirect("/articulo/" +  id);
        });
    }
}
