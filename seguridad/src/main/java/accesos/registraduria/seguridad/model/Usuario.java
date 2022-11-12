package accesos.registraduria.seguridad.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Usuario {

    private String _id;
    private String seudonimo;
    private String correo;
    private String password;

    @DBRef
    private Rol rol;

    public Usuario(String seudonimo, String correo, String password) {
        this.seudonimo = seudonimo;
        this.correo = correo;
        this.password = password;
    }
}
