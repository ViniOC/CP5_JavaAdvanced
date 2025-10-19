package Enterprise.Market.config; // Use o seu pacote de config

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Importe o HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1. Desabilita o CSRF (faremos isso por enquanto)
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                // --- ROTAS PÚBLICAS ---
                // Permite acesso à página de login
                .requestMatchers("/login").permitAll()
                // Permite acesso à página de cadastro (vamos criar)
                .requestMatchers("/signup").permitAll()
                // Permite acesso à "landing page" [cite: 60]
                .requestMatchers("/").permitAll()
                // Permite acesso a arquivos estáticos (CSS, JS, Imagens)
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Regra 1: Apenas ADMIN pode DELETAR
                .requestMatchers(HttpMethod.GET, "/produtos/deletar/**").hasRole("ADMIN")

                // Regra 2: Apenas ADMIN pode CADASTRAR/EDITAR
                .requestMatchers(HttpMethod.GET, "/produtos/novo").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/produtos/novo").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/produtos/editar/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/produtos/editar/**").hasRole("ADMIN")

                // Regra 3: Qualquer usuário LOGADO (ADMIN ou USER) pode VER a lista
                .requestMatchers("/produtos").authenticated()

                // Regra 4: Qualquer outra requisição...
                .anyRequest().authenticated() // ...exige que o usuário esteja logado
        );

        // 3. Configura o formulário de login customizado
        http.formLogin(form -> form
                .loginPage("/login") // Diz qual é a URL da nossa página de login
                .defaultSuccessUrl("/produtos", true) // Para onde vai após o login
                .permitAll() // Permite que todos acessem a página de login
        );

        // 4. Configura o logout
        http.logout(logout -> logout
                .logoutUrl("/logout") // URL para acionar o logout
                .logoutSuccessUrl("/login?logout") // Para onde vai após o logout
                .permitAll()
        );

        return http.build();
    }
}