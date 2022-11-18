package accesos.registraduria.seguridad.controller;


import accesos.registraduria.seguridad.model.Permiso;
import accesos.registraduria.seguridad.model.PermisoRol;
import accesos.registraduria.seguridad.model.Rol;
import accesos.registraduria.seguridad.repository.PermisoRepository;
import accesos.registraduria.seguridad.repository.PermisoRolRepository;
import accesos.registraduria.seguridad.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permisos-roles")
public class PermisoRolController {

    @Autowired
    private PermisoRolRepository permisoRolRepo;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private PermisoRepository permisoRepo;

    @PostMapping("/validar-permiso/rol/{id_rol}")
    public PermisoRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso,
                                 final HttpServletResponse response) throws IOException {

        Optional <Permiso> opt = this.permisoRepo.findByUrlAndMetodo(infoPermiso.getUrl(),infoPermiso.getMetodo());

        if(!opt.isPresent()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Permiso p = opt.get();

        Optional<Rol> optRol = this.rolRepo.findById(id_rol);

        if(!optRol.isPresent()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Rol r = optRol.get();

        Optional <PermisoRol> optPermisoRol = this.permisoRolRepo.findByRolAndPermiso(r,p);

        if(!optPermisoRol.isPresent()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        return optPermisoRol.get();

    }

    @GetMapping
    public List<PermisoRol> index(){
        return this.permisoRolRepo.findAll();
    }

    @GetMapping("{id}")
    public PermisoRol show(@PathVariable String id){
        Optional<PermisoRol> optRolPermiso = this.permisoRolRepo.findById(id);
        return optRolPermiso.orElse(null);
    }

    @PostMapping("")
    public PermisoRol create(@RequestBody PermisoRol p){

        Optional<Permiso> optPermiso = this.permisoRepo.findById(p.getPermiso().get_id());
        if(optPermiso.isEmpty()){
            return null;
        }

        Optional<Rol> optRol = this.rolRepo.findById(p.getRol().get_id());
        if(optRol.isEmpty()){
            return null;
        }
        return this.permisoRolRepo.save(p);
    }

    @PutMapping("{id}")
    public PermisoRol update(@PathVariable String id, @RequestBody PermisoRol rp){
        Optional<PermisoRol> optRolPermiso = this.permisoRolRepo.findById(id);
        if(optRolPermiso.isPresent()){
            PermisoRol actual = optRolPermiso.get();

            return this.permisoRolRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable String id){

        this.permisoRolRepo.deleteById(id);
        int resp = 1;
        return resp;
    }
}
