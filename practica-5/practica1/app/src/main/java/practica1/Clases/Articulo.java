package practica1.Clases;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.*;

@Entity
@Transactional
public class Articulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articulo_id;
    @Column
    private String titulo;
    @Column
    private String body;
    @OneToOne
    private Usuario autor;
    @Column
    private Date fecha;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comentario> listaComentarios;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Articulo_Etiqueta", 
        joinColumns = { @JoinColumn(name = "articulo_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "etiqueta_id") }
    )
    private List<Etiqueta> etiquetas = new ArrayList<>();
    // @OneToMany(cascade=CascadeType.ALL)
    @OneToOne(cascade=CascadeType.ALL)
    private Foto fotos;

    public Articulo() {
    }

    public Articulo(String titulo, String body, Usuario autor, Date fecha, List<Comentario> listaComentarios, Foto fotos) {
        this.titulo = titulo;
        this.body = body;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentarios = listaComentarios;
        this.fotos = fotos;
    }

    public long getArticulo_id() {
        return articulo_id;
    }

    public void setArticulo_id(long id) {
        this.articulo_id = id;
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

    public List<Etiqueta> getListaEtiqueta() {
        return this.etiquetas;
    }

    public String getStringEtiquetas() {
        if(etiquetas.size() != 0) {
            StringBuilder tmp = new StringBuilder();

            for(Etiqueta tag: etiquetas){
                tmp.append(tag.getName()).append(", ");
            }

            return  tmp.substring(0,tmp.length() - 2 ) ;
        }

        return "";
    }

    public void setListaEtiqueta(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void addListaEtiqueta(Etiqueta etiqueta) {
        this.etiquetas.add(etiqueta);
    }

     public Foto getFotos() {
        return fotos;
    }

    public void setFotos(Foto fotos) {
        this.fotos = fotos;
    }

}
