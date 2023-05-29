package practica1.Clases;

public class Usuario {
    private String username;
    private String nombre;
    private String password;
    private Boolean isAdmin;
    private Boolean isAuthor;

    public Usuario(String username,String nombre, String password, Boolean isAdmin, Boolean isAuthor) {
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
