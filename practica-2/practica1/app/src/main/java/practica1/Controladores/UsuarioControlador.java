package practica1.Controladores;
import org.eclipse.jetty.server.Request;
import practica1.Servicios.UsuarioServicio;
import io.javalin.*;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsuarioControlador {
    private Javalin app;
    private UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();

    public UsuarioControlador(Javalin app) {
        this.app = app;
    }

    public void aplicarRutas() {
        app.before(ctx -> {
            String username = ctx.sessionAttribute("username");

            if(username == null && !Objects.equals(((Request) ctx.req).getOriginalURI(), "/login")){
                ctx.redirect("/login");
            }
        });
        app.get("/login", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            ctx.render("login.html",modelo);
        });
        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String pass = ctx.formParam("password");
            Boolean success = usuarioServicio.verificarUsuario(username,pass);

            if(success){
                ctx.sessionAttribute("username", username);
                ctx.sessionAttribute("rol", usuarioServicio.obtenerRol(username));
                ctx.redirect("/");
            } else {
                ctx.redirect("/login");
            }
        });
        app.get("/logout", ctx -> {
            String id = ctx.req.getSession().getId();
            ctx.req.getSession().invalidate();
            ctx.redirect("/login");
        });
        app.get("/usuario/crear", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            ctx.render("register.html",modelo);
        });
        app.post("/usuario/crear", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            String name = ctx.formParam("name");
            Boolean admin = Objects.equals(ctx.formParam("rol"), "admin");
            Boolean author = Objects.equals(ctx.formParam("rol"), "author");

            usuarioServicio.crearUsuario(username,name,password,admin,author);

            ctx.redirect("/");
        });
    }
}
