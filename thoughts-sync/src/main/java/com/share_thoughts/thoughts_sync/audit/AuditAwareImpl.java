package com.share_thoughts.thoughts_sync.audit;

import com.share_thoughts.thoughts_sync.client.UserManagerClient;
import com.share_thoughts.thoughts_sync.dto.UserManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("auditAwareImpl")
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<Long> {

    private final UserManagerClient client;

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated())
        {
            UserManagerDto user = client.getMe();
            return Optional.ofNullable(user.getId());
        }
        return Optional.empty();
    }
}
