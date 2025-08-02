package com.thought_hub.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class RequestUtil {

//    private final UserManagerClient client;

    public String getBearerToken()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null)
        {
            HttpServletRequest request = attributes.getRequest();
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        return null;
    }

//    public Long getCurrentUserId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth == null ||! auth.isAuthenticated()) {
//            return null;
//        }
//        Object principal = auth.getPrincipal();
//        if (! (principal instanceof Jwt jwt))
//        {
//            return null;
//        }
//        String keycloakId = jwt.getClaim("sub").toString();
//        if (keycloakId == null) {
//            return null;
//        }
//        try {
//            UserManagerDto user = client.getUserByKeycloakIdOrUserId(keycloakId, null);
//            return user.getId();
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
