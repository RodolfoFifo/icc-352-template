package practica1.Servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import practica1.Clases.Etiqueta;

import java.util.List;

public class EtiquetaServicio extends GestionDB<Etiqueta>{
    private static EtiquetaServicio instancia;

    private EtiquetaServicio() {
        super(Etiqueta.class);
    }

    public static EtiquetaServicio getInstancia(){
        if(instancia==null){
            instancia = new EtiquetaServicio();
        }
        return instancia;
    }

    public Etiqueta getById(Long tag_id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM Etiqueta u WHERE u.etiqueta_id = :tag_id");
        query.setParameter("tag_id", tag_id);
        Etiqueta result = (Etiqueta) query.getResultList().get(0);

        return result;
    }


    public List<Etiqueta> getByTag(String tag_name){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM Etiqueta u WHERE u.name = :tag_name");
        query.setParameter("tag_name", tag_name);
        List<Etiqueta> result = query.getResultList();

        return result;
    }

    public List<Etiqueta> getByArticulo(Long articulo_id){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT t FROM Etiqueta t");
        List<Etiqueta> etiquetas = query.getResultList();

        return etiquetas;
    }

    public List<Etiqueta> getAll(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM Etiqueta c ", Etiqueta.class);
        List<Etiqueta> result = query.getResultList();

        return result;
    }
}
