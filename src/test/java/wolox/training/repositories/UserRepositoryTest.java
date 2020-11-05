package wolox.training.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.User;
import wolox.training.util.TestEntities;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private static User oneTestUser;
    private static List<User> manyTestUsers;

    @BeforeAll
    static void setUp() {
        oneTestUser = TestEntities.mockOneUser();
        manyTestUsers = TestEntities.mockManyUsers();
    }

    @Test
    void whenCallFindAllUserThenReturnAllList() {
        usersRepository.saveAll(manyTestUsers);
        List<User> userList = usersRepository.findAll();
        assertEquals(userList.get(0).getName(), manyTestUsers.get(0).getName());
        assertEquals(userList.get(0).getId(), manyTestUsers.get(0).getId());
    }

    @Test
    void whenUpdateUserThenReturnUserUpdated() {
        oneTestUser.setName("miguel");
        usersRepository.save(oneTestUser);
        Optional<User> persistedUser = usersRepository.findById(oneTestUser.getId());
        assertEquals(oneTestUser.getName(), persistedUser.get().getName());
    }

    @Test
    void whenCallFindAllByBirthdateBetweenAndNameContainingIgnoreCaseThenReturnListUser() {
        LocalDate startDate = LocalDate.of(1992, 11, 11);
        LocalDate endDate = LocalDate.of(2020, 11, 11);
        usersRepository.save(oneTestUser);
        Optional<List<User>> users = usersRepository.findAllByBirthdateBetweenAndNameContainingIgnoreCase(startDate, endDate, oneTestUser.getName());
        assertEquals(users.get().get(0).getBirthdate(), oneTestUser.getBirthdate());
    }

    @Test
    void whenCallFindAllByBirthdateBetweenAndNameContainingIgnoreCaseQueryAndParametersNullThenReturnListUser() {
        usersRepository.save(oneTestUser);
        Optional<List<User>> users = usersRepository.findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(null, null, null);
        assertEquals(users.get().get(0).getBirthdate(), oneTestUser.getBirthdate());
    }

}
