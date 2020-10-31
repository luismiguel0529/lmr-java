package wolox.training.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.User;
import wolox.training.repositories.UsersRepository;
import wolox.training.util.TestEntities;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomAuthenticationProviderTest {

    private static User user;
    private Authentication authentication;

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Test
    void whenAuthenticationWithUserThenReturnIsAuthenticated(){
        user = TestEntities.mockOneUser();
        authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),new ArrayList<>());
        given(usersRepository.findByUsername(authentication.getName())).willReturn(Optional.of(user));



    }
}
