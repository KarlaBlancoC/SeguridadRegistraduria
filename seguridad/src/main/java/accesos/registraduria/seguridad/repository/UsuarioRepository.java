package accesos.registraduria.seguridad.repository;

import accesos.registraduria.seguridad.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {

    Optional<Usuario> findByCorreoAndPassword(String correo, String password);
}
