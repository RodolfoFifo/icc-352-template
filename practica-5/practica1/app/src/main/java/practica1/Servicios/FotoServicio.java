package practica1.Servicios;

import practica1.Clases.Foto;

public class FotoServicio extends GestionDB<Foto> {
     private static FotoServicio instancia;

    private FotoServicio() {
        super(Foto.class);
    }

    public static FotoServicio getInstancia(){
        if(instancia==null){
            instancia = new FotoServicio();
        }
        return instancia;
    }
}
