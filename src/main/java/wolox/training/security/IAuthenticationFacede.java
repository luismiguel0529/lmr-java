package wolox.training.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacede {
    Authentication getAutentication();
}
