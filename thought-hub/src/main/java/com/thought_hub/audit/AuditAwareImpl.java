package com.thought_hub.audit;


import com.thought_hub.client.UserManagerClient;
import com.thought_hub.dto.UserManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
