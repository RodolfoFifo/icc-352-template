package practica1.Clases;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String client;
    @Column
    private String sender;
    @Column
    private Date fecha_registro;
    @Column
    private String mensaje;

    public Mensaje() {}

    public Mensaje(String client, Date fecha_registro, String mensaje, String sender) {
        this.client = client;
        this.fecha_registro = fecha_registro;
        this.mensaje = mensaje;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
