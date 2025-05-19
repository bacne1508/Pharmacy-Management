package vn.com.pharmacity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1) // Đặt thứ tự cho cấu hình này nếu cần thiết
public class SecurityDependenciesConfig {

    // Nếu CustomUserDetailsService được inject từ nơi khác, bạn cần inject nó vào đây
    // Hoặc nếu CustomUserDetailsService không có phụ thuộc phức tạp, bạn có thể tạo nó ở đây
    // Ví dụ: nếu CustomUserDetailsService chỉ cần UsersRepository
    // private final UsersRepository usersRepository;
    // public SecurityDependenciesConfig(UsersRepository usersRepository) {
    // this.usersRepository = usersRepository;
    // }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("SecurityDependenciesConfig: Attempting to create PasswordEncoder bean...");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("SecurityDependenciesConfig: PasswordEncoder bean created successfully.");
        return encoder;
    }
    
    // CÁCH 1: Nếu CustomUserDetailsService đã là một @Service bean
    // Bạn có thể inject nó vào đây và chỉ cần expose nó như một UserDetailsService bean
    // private final CustomUserDetailsService customUserDetailsService;
    // public SecurityDependenciesConfig(CustomUserDetailsService customUserDetailsService) {
    //     System.out.println("SecurityDependenciesConfig: Constructor with CustomUserDetailsService");
    //     this.customUserDetailsService = customUserDetailsService;
    // }
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     System.out.println("SecurityDependenciesConfig: Providing UserDetailsService bean from injected CustomUserDetailsService.");
    //     return customUserDetailsService;
    // }

    // CÁCH 2: Nếu bạn muốn tạo CustomUserDetailsService bean tại đây
    // (Yêu cầu UsersRepository phải có sẵn)
    // @Bean
    // public UserDetailsService userDetailsService(UsersRepository usersRepository) {
    //     System.out.println("SecurityDependenciesConfig: Creating new CustomUserDetailsService bean.");
    //     return new CustomUserDetailsService(usersRepository);
    // }
}
