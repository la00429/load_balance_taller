package co.edu.uptc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get-message")
    public String getMessage() {
        // Obtener información del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        String serviceUrl = "http://client-service/message"; // Use the service name registered in Eureka
        String clientMessage = restTemplate.getForObject(serviceUrl, String.class);
        
        return "🔒 Mensaje seguro para usuario: " + username + " | " + clientMessage;
    }

    @GetMapping("/health")
    public String health() {
        return "Load Balancer Service is running with JWT Security! 🚀";
    }
}
