package accesos.registraduria.seguridad.repository;

import accesos.registraduria.seguridad.model.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolRepository extends MongoRepository<Rol,String> {
}
