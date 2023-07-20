package practica1.Clases;
import jakarta.persistence.*;


import java.io.Serializable;

@Entity
public class Foto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foto_id;
    @Column
    private String name;
    @Column
    private String mimeType;
    @Lob
    private String fotoBase64;

    public Foto() {
    }

    public Foto(String name,String mimeType, String fotoBase64){
        this.name = name;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
    }

    public Long getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(Long id) {
        this.foto_id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public String getName(){
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

}
