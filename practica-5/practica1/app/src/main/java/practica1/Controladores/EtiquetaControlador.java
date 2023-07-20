package practica1.Controladores;
import io.javalin.Javalin;
import practica1.Servicios.EtiquetaServicio;

import java.util.*;

public class EtiquetaControlador {
    private Javalin app;

    public EtiquetaControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.get("/etiquetas", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("etiquetas", EtiquetaServicio.getInstancia().getAll());
            ctx.render("etiquetas.html", modelo);
        });        
        app.get("/etiquetas/{id}", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            Long tag_id = Long.parseLong(ctx.pathParam("id"));
            modelo.put("articulos", EtiquetaServicio.getInstancia().getById(tag_id).getArticulos());
            ctx.render("index.html", modelo);
        });
    }
}