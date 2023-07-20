package practica1.Servicios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import kotlin.Pair;
import practica1.Clases.Articulo;
import practica1.Clases.Mensaje;

import java.util.Date;
import java.util.List;

public class MensajeServicio extends GestionDB<Mensaje>{
    private static MensajeServicio instancia;

    private MensajeServicio() {
        super(Mensaje.class);
    }

    public static MensajeServicio getInstancia(){
        if(instancia==null){
            instancia = new MensajeServicio();
        }
        return instancia;
    }

    public void creaMensaje(String cliente, String mensaje, String sender) {
        Date fecha_registro = new Date();
        Mensaje msg = new Mensaje(cliente, fecha_registro, mensaje, sender);

        this.crear(msg);
    }

    public List<String> getClient(String sender) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery( "SELECT DISTINCT(m.sender) FROM Mensaje m Where m.sender not in (SELECT u.username FROM Usuario u)", String.class);
        List<String> result = query.getResultList();

        return result;
    }

    public List<Mensaje> getMessages(String sender, String client) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery( "SELECT m FROM Mensaje m WHERE m.sender = '" + sender + "' and m.client = '" + client + "' or m.sender = '" + client + "' and m.client = '" + sender + "'", Mensaje.class);
        List<Mensaje> result = query.getResultList();

        return result;
    }

    public List<String> getClients() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u.username FROM Usuario u WHERE u.isAdmin = true or u.isAuthor = true", String.class);
        List<String> result = query.getResultList();

        return result;
    }
}
