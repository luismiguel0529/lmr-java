package wolox.training.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.User;
import wolox.training.util.TestEntities;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private static User oneTestUser;
    private static User twoTestUser;
    private static List<User> manyTestUsers;

    @BeforeAll
    static void setUp() {
        oneTestUser = TestEntities.mockOneUser();
        manyTestUsers = TestEntities.mockManyUsers();
        twoTestUser = TestEntities.mockUserToPersis();
    }

    @Test
    void whenCallFindALlUserThenReturnAllList() {
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

}
