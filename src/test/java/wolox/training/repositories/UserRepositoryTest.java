package wolox.training.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void whenSaveUserThenJpaPersisted() {
        User oneTestUser = new User();
        oneTestUser.setId(1L);
        oneTestUser.setUsername("luismiguel");
        oneTestUser.setName("Luis Miguel");
        oneTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        oneTestUser.setBooks(new ArrayList<>());

        usersRepository.save(oneTestUser);

        Optional<User> user = usersRepository.findById(1L);
        assertEquals(user.get().getId(), oneTestUser.getId());
    }
}
