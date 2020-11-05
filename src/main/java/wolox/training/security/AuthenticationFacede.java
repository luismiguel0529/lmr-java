package wolox.training.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacede implements IAuthenticationFacede {
    @Override
    public Authentication getAutentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
