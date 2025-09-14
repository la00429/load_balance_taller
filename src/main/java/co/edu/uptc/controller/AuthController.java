package co.edu.uptc.controller;

import co.edu.uptc.model.AuthRequest;
import co.edu.uptc.model.AuthResponse;
import co.edu.uptc.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Autenticar las credenciales
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), 
                    authRequest.getPassword()
                )
            );

            // Si la autenticación es exitosa, generar el token
            String token = jwtUtil.generateToken(authRequest.getUsername());
            
            return ResponseEntity.ok(new AuthResponse(token, "Login exitoso"));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, "Credenciales inválidas"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new AuthResponse(null, "Error interno del servidor"));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    return ResponseEntity.ok(new AuthResponse(null, "Token válido para usuario: " + username));
                }
            }
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Token inválido"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Error al validar token"));
        }
    }
}
