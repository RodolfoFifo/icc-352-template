package practica1.Servicios;
import java.io.IOException;
import java.util.*;
import java.io.*;

import io.javalin.http.UploadedFile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import practica1.Clases.Articulo;
import practica1.Clases.Comentario;
import practica1.Clases.Etiqueta;
import practica1.Clases.Foto;
import practica1.Clases.Usuario;

public class ArticuloServicio extends GestionDB<Articulo>{
    private static ArticuloServicio instancia;

    private ArticuloServicio() {
        super(Articulo.class);
    }

    public static ArticuloServicio getInstancia(){
        if(instancia==null){
            instancia = new ArticuloServicio();
        }
        return instancia;
    }

    public void crearArticulo(String titulo, String body, String username, UploadedFile upload, String[] tags) throws IOException  {
        Date fecha = new Date();
        Usuario autor = UsuarioServicio.getInstancia().getByUsername(username).get(0);
        List<Comentario> listaComentarios =  new ArrayList<>();
        byte[] bytes = upload.getContent().readAllBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        Foto foto = new Foto(upload.getFilename(), upload.getContentType(), encodedString);

        Articulo articulo = ArticuloServicio.getInstancia().crear(new Articulo(titulo, body, autor, fecha, listaComentarios, foto));

        for(String tag: tags){
            List<Etiqueta> ts= EtiquetaServicio.getInstancia().getByTag(tag);
            Etiqueta t = null;

            if(ts.size() == 0 ){
                t = EtiquetaServicio.getInstancia().crear(new Etiqueta(tag));
            } else {
                t = ts.get(0);
            }

            articulo.addListaEtiqueta(t);
        }

        ArticuloServicio.getInstancia().editar(articulo);
    }

    public void editarArticulo(String titulo, String body, String[] tags, Long id){
        Articulo articulo = this.getArticulo(id);

        articulo.setTitulo(titulo);
        articulo.setBody(body);
        articulo.setListaEtiqueta(new ArrayList<>());

        for(String tag: tags){
            List<Etiqueta> ts= EtiquetaServicio.getInstancia().getByTag(tag);
            Etiqueta t = null;

            if(ts.size() == 0 ){
                t = EtiquetaServicio.getInstancia().crear(new Etiqueta(tag));
            } else {
                t = ts.get(0);
            }

            articulo.addListaEtiqueta(t);
        }

        ArticuloServicio.getInstancia().editar(articulo);
    }

    public void eliminarArticulo(Long id){
        EntityManager em = getEntityManager();
        Articulo articulo = this.getArticulo(id);
        articulo.setListaEtiqueta(new ArrayList<>());
        articulo.setListaComentarios(new ArrayList<>());
        articulo.setFotos(null);
        ArticuloServicio.getInstancia().editar(articulo);
        ArticuloServicio.getInstancia().eliminar(articulo);
    }

    public Articulo getArticulo(long id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT a FROM Articulo a WHERE a.id = " + id, Articulo.class);
        Articulo result = (Articulo) query.getResultList().get(0);

        return result;
    }

    public List<Articulo> getArticulos(int pag){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT a FROM Articulo a", Articulo.class);
        query.setFirstResult(pag);
        query.setMaxResults(pag + 5);
        List<Articulo> result = result = query.getResultList();

        return result;
    }

    public List<Articulo> getByTag(long tag_id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT a FROM Articulo a Where :tag_id in a.", Articulo.class);
        query.setParameter("tag_id", tag_id);
        List<Articulo> result = query.getResultList();

        return result;
    }

    public void mappedTag(Long articulo_id,Long tag_id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("INSERT INTO Articulo_Etiqueta (ARTICULO_ID, LISTAETIQUETA_ID) VALUES (" + articulo_id +", " + tag_id +")");
        query.executeUpdate();
    }
}
