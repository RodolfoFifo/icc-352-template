package practica1.Controladores;
import io.javalin.Javalin;
import practica1.Servicios.ArticuloServicio;
import practica1.Servicios.ComentarioServicio;

import java.util.*;
import java.util.stream.Collectors;

public class ArticuloControlador {
    private Javalin app;
    private ArticuloServicio articuloServicio = ArticuloServicio.getInstancia();
    private ComentarioServicio comentarioServicio = ComentarioServicio.getInstancia();

    public ArticuloControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas(){

        app.get("/", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("articulos", articuloServicio.getArticulos());
            ctx.render("index.html", modelo);
        });
        app.get("/articulos", ctx -> {
            ctx.render("form_articulos.html");
        });
        app.get("/articulo/editar/{id}", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("articulo", articuloServicio.getArticulo(Long.parseLong(ctx.pathParam("id"))));
            ctx.render("update_articulos.html", modelo);
        });
        app.post("/articulo/editar/{id}", ctx -> {
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String tags = ctx.formParam("etiqueta").replace(" , ", ",").replace(" ,",",").replace(", ",",");
            String[] etiquetas = tags.split(",");
            String id = ctx.pathParam("id");
            articuloServicio.editarArticulo(titulo,cuerpo,etiquetas,Long.parseLong(id));

            ctx.redirect("/articulo/" + id);
        });
        app.post("/articulo/eliminar/{id}", ctx -> {
            String id = ctx.pathParam("id");
            articuloServicio.eliminarArticulo(Long.parseLong(id));

            ctx.redirect("/");
        });
        app.get("/articulo/{id}", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("articulo", articuloServicio.getArticulo(Long.parseLong(ctx.pathParam("id"))));
            modelo.put("comentarios", comentarioServicio.obtenerComentario(Long.parseLong(ctx.pathParam("id"))));
            ctx.render("articulo.html",modelo);
        });
        app.post("/articulos", ctx -> {
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String tags = ctx.formParam("etiqueta").replace(" , ", ",").replace(" ,",",").replace(", ",",");
            String[] etiquetas = tags.split(",");
            String user = ctx.sessionAttribute("username");
            articuloServicio.crearArticulo(titulo,cuerpo,user,etiquetas);

            ctx.redirect("/");
        });
    }
}
