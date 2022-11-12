package accesos.registraduria.seguridad.repository;

import accesos.registraduria.seguridad.model.Permiso;
import accesos.registraduria.seguridad.model.PermisoRol;
import accesos.registraduria.seguridad.model.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PermisoRolRepository extends MongoRepository<PermisoRol,String> {
    Optional<PermisoRol> findByRolAndPermiso(Rol rol, Permiso permiso);
}
