package practica1.Servicios;
import java.util.*;
import java.util.stream.Collectors;

import practica1.Clases.Articulo;
import practica1.Clases.Comentario;
import practica1.Clases.Etiqueta;
import practica1.Clases.Usuario;

public class ArticuloServicio {
    private static ArticuloServicio instancia;
    private UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private int count = 0;
    private int count_t = 0;
    private List<Articulo> listaArticulos = new ArrayList<>();

    public static ArticuloServicio getInstancia(){
        if(instancia==null){
            instancia = new ArticuloServicio();
        }
        return instancia;
    }

    public void crearArticulo(String titulo, String body, String username, String[] tags){
        Date fecha = new Date();
        Usuario autor = usuarioServicio.obtenerUsuario(username);
        List<Comentario> listaComentarios =  new ArrayList<>();
        List<Etiqueta> listaEtiqueta = new ArrayList<>();

        for(String tag: tags){
            Etiqueta t = new Etiqueta(count_t, tag);
            listaEtiqueta.add(t);
            count_t+=1;
        }
        
        Articulo articulo = new Articulo(count, titulo, body, autor, fecha, listaComentarios, listaEtiqueta);
        listaArticulos.add(0,articulo);
        count += 1;
    }

    public void editarArticulo(String titulo, String body, String[] tags, Long id){
        for(int i = 0;i<listaArticulos.size();i++){
            if(listaArticulos.get(i).getId() == id){
                List<Etiqueta> listaEtiqueta = new ArrayList<>();

                for(String tag: tags){
                    Etiqueta t = new Etiqueta(count_t, tag);
                    listaEtiqueta.add(t);
                    count_t+=1;
                }

                listaArticulos.get(i).setTitulo(titulo);
                listaArticulos.get(i).setBody(body);
                listaArticulos.get(i).setListaEtiqueta(listaEtiqueta);

                break;
            }
        }
    }

    public void eliminarArticulo(Long id){
        for(int i = 0;i<listaArticulos.size();i++){
            if(listaArticulos.get(i).getId() == id){
                listaArticulos.remove(i);
                break;
            }
        }
    }

    public List<Articulo> getArticulos(){
        return listaArticulos;
    }

    public Articulo getArticulo(long id){

        for(Articulo articulo: listaArticulos){
            if(articulo.getId() == id){
                return articulo;
            }
        }

        return null;
    }
}
