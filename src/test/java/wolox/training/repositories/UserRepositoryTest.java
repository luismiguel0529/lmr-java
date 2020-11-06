package wolox.training.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private static User testUser;
    private static List<User> testUsers;

    @BeforeAll
    static void setUp() {
        testUser = TestEntities.mockOneUser();
        testUsers = TestEntities.mockManyUsers();
    }

    @Test
    void whenCallFindAllUserThenReturnAllList() {
        usersRepository.saveAll(testUsers);
        List<User> userList = usersRepository.findAll();
        assertEquals(userList.get(0).getName(), testUsers.get(0).getName());
        assertEquals(userList.get(0).getId(), testUsers.get(0).getId());
    }

    @Test
    void whenUpdateUserThenReturnUserUpdated() {
        usersRepository.save(testUser);
        testUser.setName("miguel");
        usersRepository.save(testUser);
        Optional<User> persistedUser = usersRepository.findById(testUser.getId());
        assertEquals(testUser.getName(), persistedUser.get().getName());
    }

    @Test
    void whenCallFindAllByBirthdateBetweenAndNameContainingIgnoreCaseQueryThenReturnListUser() {
        Pageable pageable = PageRequest.of(0, 1);
        LocalDate startDate = LocalDate.of(1992, 11, 11);
        LocalDate endDate = LocalDate.of(2020, 11, 11);
        usersRepository.save(testUser);
        Page<User> users = usersRepository.findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(startDate, endDate, testUser.getName(), pageable);
        assertEquals(users.getContent().iterator().next().getBirthdate(), testUser.getBirthdate());
    }

    @Test
    void whenCallFindAllByBirthdateBetweenAndNameContainingIgnoreCaseQueryAndParametersNullThenReturnListUser() {
        usersRepository.save(testUser);
        Page<User>users = usersRepository.findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(null, null, "",null);
        assertEquals(users.getContent().iterator().next().getBirthdate(), testUser.getBirthdate());
    }
}
