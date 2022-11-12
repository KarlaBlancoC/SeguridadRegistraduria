package accesos.registraduria.seguridad.controller;

import accesos.registraduria.seguridad.model.Permiso;
import accesos.registraduria.seguridad.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoRepository permisoRepo;

    @GetMapping("")
    public List<Permiso> index() {
        return this.permisoRepo.findAll();
    }

    @GetMapping("/{id}")
    public Permiso show(@PathVariable String id){
        Optional<Permiso> opt = this.permisoRepo.findById(id);
        return opt.orElse(null);
    }

    @PostMapping("")
    public Permiso create(@RequestBody Permiso r){
        return this.permisoRepo.save(r);
    }

    @PutMapping("/{id}")
    public Permiso update(@PathVariable String id, @RequestBody Permiso p){
        Optional<Permiso> opt = this.permisoRepo.findById(id);
        if(opt.isPresent()){
            Permiso actual = opt.get();
            actual.setUrl(p.getUrl());
            actual.setMetodo(p.getMetodo());
            return this.permisoRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){

        Optional<Permiso> opt = this.permisoRepo.findById(id);
        if(opt.isPresent()) {
            this.permisoRepo.deleteById(id);
        }
    }
}
