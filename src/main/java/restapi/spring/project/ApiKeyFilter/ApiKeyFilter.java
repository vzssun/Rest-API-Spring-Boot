// package restapi.spring.project.ApiKeyFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import io.micrometer.common.lang.NonNull;

// import java.io.IOException;

// @Component // basically an actor model
// public class ApiKeyFilter extends OncePerRequestFilter {

//     @Value("${remote-service.api-key}") // from application.yml
//     private String masterApiKey;

//     @Override
//     protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                     @NonNull HttpServletResponse response,
//                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
        
//         // key from the Header
//         String requestKey = request.getHeader("X-API-KEY");

//         // path is public?
//         if (request.getServletPath().contains("/api/auth")) {
//             filterChain.doFilter(request, response); // Skip
//             return;
//         }

//         // Validate
//         if (masterApiKey.equals(requestKey)) {
//             // correct
//             filterChain.doFilter(request, response);
//         } else {
//             // wrong
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("Invalid API Key");
//         }
//     }
// }