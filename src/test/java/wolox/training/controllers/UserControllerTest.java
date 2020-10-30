package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersRepository mockUsersRepository;
    @MockBean
    private BookRepository mockBookRepository;

    private static User oneTestUser;
    private static User twoTestUser;
    private static User treeTestUser;
    private static Book oneTestBook;
    private static List<User> manyTestUsers;
    private static List<Book> manyTestBooks;
    private static final String USER_PATH = "/api/users";

    @BeforeAll
    static void setUp() {

        oneTestUser = new User();
        oneTestUser.setId(1l);
        oneTestUser
                .username("luismiguel")
                .name("Luis Miguel")
                .birthdate(LocalDate.of(1993, 11, 23))
                .setBooks(new ArrayList<>());

        twoTestUser = new User();
        twoTestUser.username("Miguel")
                .name("Miguel")
                .birthdate(LocalDate.of(1994, 1, 2))
                .setBooks(new ArrayList<>());

        manyTestUsers = new ArrayList<>();
        manyTestUsers.add(oneTestUser);

        oneTestBook = new Book();
        oneTestBook.setId(1l);

        manyTestBooks = new ArrayList<>();
        manyTestBooks.add(oneTestBook);

        treeTestUser = new User();
        treeTestUser.setId(1L);
        treeTestUser
                .username("luis")
                .name("Luis")
                .birthdate(LocalDate.of(1993, 11, 23))
                .setBooks(manyTestBooks);
    }

    @Test
    void whenFindUserByIdThenReturnStatusOK() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(oneTestUser));
        String url = (USER_PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenFindAllUserTHenReturnStatusOK() throws Exception {
        given(mockUsersRepository.findAll()).willReturn(manyTestUsers);
        String url = USER_PATH;
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenCreateUserThenReturnStatusCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(oneTestUser);
        String url = USER_PATH;
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void whenUpdateUserThenReturnStatusCreated() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(oneTestUser));
        String json = new ObjectMapper().writeValueAsString(twoTestUser);
        String url = (USER_PATH + "/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void whenDeleteUserThenReturnStatusNoContent() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(oneTestUser));
        String url = (USER_PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void whenAddBookThenReturnStatusCreated() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(oneTestUser));
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(oneTestBook));
        String url = (USER_PATH + "/1/add-books/1");
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void whenRemoveBookThenReturnStatusNoContent() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(treeTestUser));
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(oneTestBook));
        String url = (USER_PATH + "/1/remove-books/1");
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
