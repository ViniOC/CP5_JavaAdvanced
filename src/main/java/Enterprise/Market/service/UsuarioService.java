package Enterprise.Market.service;

import Enterprise.Market.dto.UsuarioSignupDTO;
import Enterprise.Market.entity.Role;
import Enterprise.Market.entity.Usuario;
import Enterprise.Market.repository.RoleRepository;
import Enterprise.Market.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrar(UsuarioSignupDTO dto) {

        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Nome de usuário já existe: " + dto.getUsername());
        }

        Role userRole = roleRepository.findByNome("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(dto.getUsername());

        novoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        novoUsuario.setRoles(Collections.singleton(userRole));

        usuarioRepository.save(novoUsuario);
    }
}