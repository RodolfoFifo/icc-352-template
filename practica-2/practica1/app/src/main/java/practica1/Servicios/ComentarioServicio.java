package practica1.Servicios;

import org.checkerframework.checker.units.qual.C;
import practica1.Clases.Articulo;
import practica1.Clases.Comentario;
import practica1.Clases.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ComentarioServicio {
    private ArticuloServicio articuloServicio = ArticuloServicio.getInstancia();

    private UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private static ComentarioServicio instancia;
    private int count = 0;
    private List<Comentario> listaComentario = new ArrayList<>();

    public static ComentarioServicio getInstancia(){
        if(instancia==null){
            instancia = new ComentarioServicio();
        }
        return instancia;
    }

    public void crearComentario(long articulo_id, String body, String usuario) {
        Articulo articulo = this.articuloServicio.getArticulo(articulo_id);
        Usuario user = usuarioServicio.obtenerUsuario(usuario);
        Comentario comentario = new Comentario(count,body,user,articulo);
        listaComentario.add(comentario);
        count += 1;
    }

    public List<Comentario> obtenerComentario(long articulo_id) {
        List<Comentario> tmp = new ArrayList<>();

        for (Comentario comentario:listaComentario) {
            if(comentario.getArticulo().getId() == articulo_id) {
                tmp.add(comentario);
            }
        }

        return tmp;
    }
}
