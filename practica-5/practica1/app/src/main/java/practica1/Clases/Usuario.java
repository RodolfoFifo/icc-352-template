package practica1.Clases;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    private String username;
    @Column
    private String nombre;
    @Column
    private String password;
    @Column
    private Boolean isAdmin;
    @Column
    private Boolean isAuthor;

    public Usuario() {
    }

    public Usuario(String username, String nombre, String password, Boolean isAdmin, Boolean isAuthor) {
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isAuthor = isAuthor;
    }

    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getAuthor() {
        return isAuthor;
    }

    public void setAuthor(Boolean author) {
        isAuthor = author;
    }
}
