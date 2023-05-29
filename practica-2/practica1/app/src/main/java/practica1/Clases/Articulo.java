package practica1.Clases;
import java.util.*;
import java.util.stream.Collectors;

public class Articulo {
    private long id;
    private String titulo;
    private String body;
    private Usuario autor;
    private Date fecha;
    private List<Comentario> listaComentarios;
    private List<Etiqueta> listaEtiqueta;

    public Articulo(long id, String titulo, String body, Usuario autor, Date fecha, List<Comentario> listaComentarios, List<Etiqueta> listaEtiqueta) {
        this.id = id;
        this.titulo = titulo;
        this.body = body;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = listaComentarios;
        this.listaEtiqueta = listaEtiqueta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public String getListaEtiqueta() {
        StringBuilder tmp = new StringBuilder();

        for(Etiqueta tag: listaEtiqueta){
            tmp.append(tag.getEtiqueta()).append(", ");
        }


        return  tmp.substring(0,tmp.length() - 2 ) ;
    }

    public void setListaEtiqueta(List<Etiqueta> listaEtiqueta) {
        this.listaEtiqueta = listaEtiqueta;
    }
}
