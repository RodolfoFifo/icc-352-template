package practica1.Clases;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.*;

@Entity
@Transactional
public class Etiqueta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long etiqueta_id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "etiquetas")
    private List<Articulo> articulos = new ArrayList<>();


    public Etiqueta() {
    }

    public Etiqueta(String etiqueta) {
        this.name = etiqueta;
    }

    public long getEtiqueta_id() {
        return etiqueta_id;
    }

    public void setEtiqueta_id(long id) {
        this.etiqueta_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public void addArticulos(Articulo articulo) {
        this.articulos.add(articulo);
    }
}
