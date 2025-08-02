package com.share_thoughts.thoughts_sync.util;

import com.share_thoughts.thoughts_sync.client.UserManagerClient;
import com.share_thoughts.thoughts_sync.dto.UserManagerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
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
