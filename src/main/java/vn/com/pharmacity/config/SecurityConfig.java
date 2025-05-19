package vn.com.pharmacity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeRequests(auth -> auth
            .antMatchers("/", "/api/auth/**", "/signUp","/static/**","/fonts/**","/roboto-font/**", "/css/**", "/js/**", "/error").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/doctor/**").hasRole("DOCTOR")
            .antMatchers("/reception/**").hasRole("RECEPTION")
            .antMatchers("/pharmacy/**").hasRole("PHARMACIST")
            .antMatchers("/lab/**").hasRole("LAB_ASSISTANT")
            .antMatchers("/cashier/**").hasRole("CASHIER")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/")
            .defaultSuccessUrl("/home", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/?logout")
        )
        .authenticationManager(authenticationManager); 

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        System.out.println("SecurityConfig: Attempting to get AuthenticationManager from AuthenticationConfiguration.");
        System.out.println("SecurityConfig (AM from AuthConfig): UserDetailsService available: " + (userDetailsService != null));
        System.out.println("SecurityConfig (AM from AuthConfig): PasswordEncoder available: " + (passwordEncoder != null));

        // Spring Boot sẽ tự động cấu hình AuthenticationManager từ AuthenticationConfiguration
        // để sử dụng UserDetailsService và PasswordEncoder beans có sẵn trong context.

        // Nếu AuthenticationConfiguration.getAuthenticationManager() vẫn gây ra vấn đề
        // (do "No authenticationProviders..." từ một builder ngầm nào đó),
        // bạn có thể cần phải tự tạo ProviderManager.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // userDetailsService từ SecurityDependenciesConfig
        provider.setPasswordEncoder(passwordEncoder);     // passwordEncoder từ SecurityDependenciesConfig
        // provider.afterPropertiesSet(); // Không bắt buộc nhưng có thể gọi để khởi tạo sớm

        ProviderManager providerManager = new ProviderManager(provider);
        // Bạn có thể muốn cấu hình thêm cho ProviderManager ở đây nếu cần,
        // ví dụ: parentAuthenticationManager, eraseCredentialsAfterAuthentication
        // providerManager.setEraseCredentialsAfterAuthentication(false); // Để debug nếu cần

        System.out.println("SecurityConfig: Manually created ProviderManager: " + providerManager);
        return providerManager;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
