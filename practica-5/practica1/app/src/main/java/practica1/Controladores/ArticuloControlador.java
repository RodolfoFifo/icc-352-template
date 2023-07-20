package practica1.Controladores;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.UploadedFile;
import org.jasypt.util.text.BasicTextEncryptor;
import practica1.Servicios.ArticuloServicio;
import practica1.Servicios.ComentarioServicio;
import practica1.Servicios.FotoServicio;
import practica1.Clases.Articulo;

import java.util.*;


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
            int pag = (ctx.sessionAttribute("pag") != null) ? Integer.parseInt(ctx.sessionAttribute("pag")) : 0;
            int next = (ctx.queryParam("next") != null) ? Integer.parseInt(ctx.queryParam("next")) : 0;
            int move = pag + next;

            if(move < 0){
                ctx.sessionAttribute("pag", "0");
            } else if (articuloServicio.getArticulos(move).size() != 0 && ctx.queryParam("next") != null){
                ctx.sessionAttribute("pag", String.valueOf(move));
                pag = move;
            }

            List<Articulo> a = articuloServicio.getArticulos(pag);

            modelo.put("articulos", (a.size() <= 5) ? a : a.subList(0, 5));
            modelo.put("pag", pag);
            ctx.render("index.html", modelo);
        });
        app.post("/listado", ctx -> {
            int pag = (ctx.sessionAttribute("pag") != null) ? Integer.parseInt(ctx.sessionAttribute("pag")) : 0;
            int next = (ctx.queryParam("next") != null) ? Integer.parseInt(ctx.queryParam("next")) : 0;
            int move = pag + next;

            if(move < 0){
                ctx.sessionAttribute("pag", "0");
            } else if (articuloServicio.getArticulos(move).size() != 0 && ctx.queryParam("next") != null){
                ctx.sessionAttribute("pag", String.valueOf(move));
                pag = move;
            }

            Map<String, Object> modelo = new HashMap<String, Object>();
            List<Articulo> a = articuloServicio.getArticulos(pag);
            a = (a.size() <= 5) ? a : a.subList(0, 5);
            String json = "[";

            for(Articulo articulo:a) {
                json = json + "{\"id\": " + articulo.getArticulo_id()  + ", \"titulo\": \"" + articulo.getTitulo() + "\", \"etiquetas\": \"" + articulo.getStringEtiquetas() + "\", \"contenido\": \"" + articulo.getBody() + "\"}, ";
            };

            json = json.substring(0, json.length() - 2) + "]";
            ctx.result(json);
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
            Articulo articulo = articuloServicio.getArticulo(Long.parseLong(ctx.pathParam("id")));
            modelo.put("articulo", articulo);
            modelo.put("comentarios", comentarioServicio.obtenerComentario(Long.parseLong(ctx.pathParam("id"))));
            modelo.put("fotos", FotoServicio.getInstancia().find(articulo.getFotos().getFoto_id()));
            ctx.render("articulo.html",modelo);
        });
        app.post("/articulos", ctx -> {
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String tags = ctx.formParam("etiqueta").replace(" , ", ",").replace(" ,",",").replace(", ",",");
            String[] etiquetas = tags.split(",");
            String user = ctx.sessionAttribute("username");
            UploadedFile upload = ctx.uploadedFile("foto");
            articuloServicio.crearArticulo(titulo, cuerpo,user, upload, etiquetas);
            ctx.redirect("/");
        });
    }
}
