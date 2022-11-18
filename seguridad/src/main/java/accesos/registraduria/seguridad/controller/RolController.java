package accesos.registraduria.seguridad.controller;

import accesos.registraduria.seguridad.model.Rol;
import accesos.registraduria.seguridad.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolRepository rolRepo;

    @GetMapping("")
    public List<Rol> index(){
        return this.rolRepo.findAll();
    }

    @GetMapping("{id}")
    public Rol show(@PathVariable String id){
        Optional<Rol> optRol = this.rolRepo.findById(id);
        return optRol.orElse(null);
    }

    @PostMapping("")
    public Rol create(@RequestBody Rol r){
        return this.rolRepo.save(r);
    }

    @PutMapping("{id}")
    public Rol update(@PathVariable String id, @RequestBody Rol r){
        Optional<Rol> optRol = this.rolRepo.findById(id);
        if(optRol.isPresent()){
            Rol actual = optRol.get();
            actual.setNombre(r.getNombre());
            actual.setDescripcion(r.getDescripcion());
            return this.rolRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public int delete(@PathVariable String id){

        this.rolRepo.deleteById(id);
        int resp = 1;
        return resp;
    }
}
