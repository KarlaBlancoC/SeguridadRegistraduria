package accesos.registraduria.seguridad.repository;

import accesos.registraduria.seguridad.model.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PermisoRepository extends MongoRepository<Permiso,String> {

    Optional<Permiso> findByUrlAndMetodo(String url, String metodo);
}
