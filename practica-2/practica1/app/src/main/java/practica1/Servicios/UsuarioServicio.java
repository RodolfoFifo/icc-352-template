package practica1.Servicios;
import java.util.*;
import java.util.stream.Collectors;

import practica1.Clases.Usuario;

public class UsuarioServicio {
    private static UsuarioServicio instancia;
    private List<Usuario> listaUsuario = new ArrayList<>();

    public static UsuarioServicio getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    public void crearUsuario(String username,String nombre, String password, Boolean isAdmin, Boolean isAuthor) {
        Usuario user = new Usuario(username, nombre, password, isAdmin, isAuthor);
        listaUsuario.add(user);
    }

    public Usuario obtenerUsuario(String username){
        for (Usuario usuario:listaUsuario) {
            if (Objects.equals(usuario.getUsername(), username)) {
                return usuario;
            }
        }

        return null;
    }

    public String obtenerRol(String username) {
        Usuario user = this.obtenerUsuario(username);

        if (user.getAdmin()) {
            return "admin";
        } else if (user.getAuthor()) {
            return "author";
        }

        return null;
    }

    public boolean verificarUsuario(String username, String password){
        for (Usuario usuario : listaUsuario) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }


}
