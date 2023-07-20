package practica1.Servicios;
import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import practica1.Clases.Usuario;

public class UsuarioServicio extends GestionDB<Usuario>{
    private static UsuarioServicio instancia;
    private List<Usuario> listaUsuario = new ArrayList<>();

    private UsuarioServicio() {
        super(Usuario.class);
    }

    public static UsuarioServicio getInstancia(){
        if(instancia==null){
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    public Usuario obtenerUsuario(String username){
        for (Usuario usuario:listaUsuario) {
            if (Objects.equals(usuario.getUsername(), username)) {
                return usuario;
            }
        }

        return null;
    }

    public String obtenerRol(Usuario user) {

        if (user.getAdmin()) {
            return "admin";
        } else if (user.getAuthor()) {
            return "author";
        }

        return null;
    }

    public List<Usuario> getByUsername(String username){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username");
        query.setParameter("username", username);
        List<Usuario> result = query.getResultList();

        return result;
    }

    public Usuario verificarUsuario(String username, String password){
        List<Usuario> listaUsuario = getByUsername(username);

        for (Usuario usuario : listaUsuario) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }

        return null;
    }


}
