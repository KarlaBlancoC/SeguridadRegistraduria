package accesos.registraduria.seguridad.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Rol {

    private String _id;
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
