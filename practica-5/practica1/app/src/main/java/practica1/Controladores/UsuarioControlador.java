package practica1.Controladores;
import org.eclipse.jetty.server.Request;
import org.jasypt.util.text.BasicTextEncryptor;
import practica1.Clases.RegistroUsuario;
import practica1.Clases.Usuario;
import practica1.Servicios.ConexionService;
import practica1.Servicios.UsuarioServicio;
import io.javalin.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsuarioControlador {
    private Javalin app;
    private UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private BasicTextEncryptor encryptor;

    public UsuarioControlador(Javalin app) {
        encryptor = new BasicTextEncryptor();
        encryptor.setPassword("jasypt");
        this.app = app;
    }

    public void aplicarRutas() {
        app.before(ctx -> {
            String username = ctx.sessionAttribute("username");

            if(username == null && ctx.cookie("username") != null){
                username = ctx.cookie("username");
                ctx.sessionAttribute("username",encryptor.decrypt(ctx.cookie("username")));
                ctx.sessionAttribute("rol",encryptor.decrypt(ctx.cookie("rol")));
            }

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
            Boolean check = Objects.equals(ctx.formParam("check"), "on");
            Usuario user = usuarioServicio.verificarUsuario(username,pass);

            if(user != null){
                if(check) {
                    ctx.cookie("username", Objects.requireNonNull(encryptor.encrypt(username)), 604800);
                    ctx.cookie("rol", encryptor.encrypt(usuarioServicio.obtenerRol(user)), 604800);
                } else {
                    ctx.sessionAttribute("username", username);
                    ctx.sessionAttribute("rol", usuarioServicio.obtenerRol(user));
                }

                ConexionService.getInstancia().insertRegistroUsuario(new RegistroUsuario(username));
                ctx.redirect("/");
            } else {
                ctx.redirect("/login");
            }
        });
        app.get("/logout", ctx -> {
            ctx.req.getSession().invalidate();
            ctx.cookie("username","",0);
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

            UsuarioServicio.getInstancia().crear(new Usuario(username,name,password,admin,author));
            ctx.redirect("/");
        });
    }
}
