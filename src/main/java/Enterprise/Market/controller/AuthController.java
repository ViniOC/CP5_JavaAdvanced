package Enterprise.Market.controller;

import Enterprise.Market.dto.UsuarioSignupDTO;
import Enterprise.Market.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Mostra a página de login customizada.
     * Definido em SecurityConfig.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Retorna o login.html
    }

    /**
     * Mostra a "landing page".
     * Requisito do PDF.
     */
    @GetMapping("/")
    public String homePage() {
        return "index"; // Retorna o index.html
    }

    /**
     * Mostra o formulário de cadastro (Sign Up).
     */
    @GetMapping("/signup")
    public String signupPage(Model model) {
        // Envia um DTO vazio para o formulário
        model.addAttribute("usuarioDTO", new UsuarioSignupDTO());
        return "signup"; // Retorna o signup.html
    }

    /**
     * Processa o formulário de cadastro.
     */
    @PostMapping("/signup")
    public String processarSignup(@ModelAttribute("usuarioDTO") UsuarioSignupDTO dto) {

        try {
            usuarioService.registrar(dto);
        } catch (RuntimeException e) {
            // O ideal aqui é retornar para a página de signup com uma mensagem de erro
            // Mas, por simplicidade, vamos redirecionar para o login
            return "redirect:/signup?error"; // (Implementação futura)
        }

        // Redireciona para o login após o sucesso
        return "redirect:/login?success";
    }
}