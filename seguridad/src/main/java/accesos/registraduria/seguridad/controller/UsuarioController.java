package accesos.registraduria.seguridad.controller;

import accesos.registraduria.seguridad.model.Usuario;
import accesos.registraduria.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Hashtable;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/validate")
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException {
        String correo = infoUsuario.getCorreo();
        String password = infoUsuario.getPassword();
        password = tecnicaHash(password);

        Optional<Usuario> opt = this.usuarioRepo.findByCorreoAndPassword(correo,password);

        if(opt.isPresent()){
            Usuario u = opt.get();
            u.setPassword("");
            return u;
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @GetMapping("")
    public List<Usuario> index() {
        return this.usuarioRepo.findAll();
    }

    @GetMapping("/{id}")
    public Usuario show(@PathVariable String id){
        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        return opt.orElse(null);
    }

    @PostMapping("")
    public Usuario create(@RequestBody Usuario infoUsuario){
        String nuevaContrasena = tecnicaHash(infoUsuario.getPassword());
        infoUsuario.setPassword(nuevaContrasena);
        return this.usuarioRepo.save(infoUsuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario){
        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        if(opt.isPresent()){
            Usuario actual = opt.get();
            if(infoUsuario.getPassword() != null && !infoUsuario.getPassword().isBlank())
                actual.setPassword(infoUsuario.getPassword());
            if(infoUsuario.getSeudonimo() != null && !infoUsuario.getSeudonimo().isBlank())
                actual.setSeudonimo(infoUsuario.getSeudonimo());
            if(infoUsuario.getCorreo() != null && !infoUsuario.getCorreo().isBlank())
                actual.setCorreo(infoUsuario.getCorreo());
            if(infoUsuario.getRol() != null)
                actual.setRol(infoUsuario.getRol());
            return this.usuarioRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Hashtable delete(@PathVariable String id){
        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        Hashtable <String, Integer> response= new Hashtable<String, Integer>();
        if(opt.isPresent()) {
            this.usuarioRepo.deleteById(id);
            response.put("delete_count",1);
        } else{
            response.put("delete_count",0);
        }
        return response;
    }

    public String tecnicaHash(String password){

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();

    }

}
