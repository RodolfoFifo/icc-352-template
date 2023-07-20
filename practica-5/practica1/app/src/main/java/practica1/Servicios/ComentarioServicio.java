package practica1.Servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import practica1.Clases.Articulo;
import practica1.Clases.Comentario;
import practica1.Clases.Usuario;

import java.util.List;

public class ComentarioServicio extends GestionDB<Comentario> {
    private ArticuloServicio articuloServicio = ArticuloServicio.getInstancia();

    private UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private static ComentarioServicio instancia;


    private ComentarioServicio() {
        super(Comentario.class);
    }

    public static ComentarioServicio getInstancia(){
        if(instancia==null){
            instancia = new ComentarioServicio();
        }
        return instancia;
    }

    public void crearComentario(long articulo_id, String body, String usuario) {
        Articulo articulo = this.articuloServicio.getArticulo(articulo_id);
        Usuario user = usuarioServicio.obtenerUsuario(usuario);
        Comentario comentario = new Comentario(body,user,articulo);
        this.crear(comentario);
    }

    public List<Comentario> obtenerComentario(Long articulo_id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM Comentario c Where c.articulo = " + articulo_id, Comentario.class);
        List<Comentario> result = query.getResultList();

        return result;
    }
}
