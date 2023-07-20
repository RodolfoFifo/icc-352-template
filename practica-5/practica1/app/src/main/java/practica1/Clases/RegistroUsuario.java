package practica1.Clases;

import jakarta.persistence.Entity;

import java.util.Date;

public class RegistroUsuario {
    private Date fecha_registro;
    private String username;

    public RegistroUsuario(String username) {
        this.username = username;
        this.fecha_registro = new Date();
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
