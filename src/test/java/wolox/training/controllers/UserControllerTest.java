package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;
import wolox.training.security.CustomAuthenticationProvider;
import wolox.training.security.IAuthenticationFacede;
import wolox.training.service.OpenLibraryService;
import wolox.training.util.TestEntities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UsersController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersRepository mockUsersRepository;

    @MockBean
    private BookRepository mockBookRepository;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private OpenLibraryService openLibraryService;

    @MockBean
    private IAuthenticationFacede iAuthenticationFacede;

    @MockBean
    private Authentication authentication;

    private static User testUser;
    private static User twoTestUser;
    private static Book testBook;
    private static List<User> testUsers;
    private static List<Book> testBooks;
    private static final String USER_PATH = "/api/users";

    @BeforeAll
    static void setUp() {
        testUser = TestEntities.mockOneUser();
        testUsers = TestEntities.mockManyUsers();
        testBook = TestEntities.mockBook();
        twoTestUser = TestEntities.mockTwoUser();
        testBooks = new ArrayList<>();
        testBooks.add(testBook);
        twoTestUser.setBooks(testBooks);
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test find all user ,return status OK")
    void whenFindUserByIdThenReturnStatusOK() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(testUser));
        String url = (USER_PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a user is searched for its id,it return status not found")
    void whenUserThatNotExistsThenReturnNotFound() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.empty());
        String url = (USER_PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test , When a user is created , it return status Created")
    void whenCreateUserThenReturnStatusCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(testUser);
        String url = USER_PATH;
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a user is updated , it return status OK")
    void whenUpdateUserThenReturnStatusCreated() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(testUser));
        String json = new ObjectMapper().writeValueAsString(testUser);
        String url = (USER_PATH + "/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a user is updated , it return status No Found")
    void whenUpdateUserThenReturnStatusNoFound() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(testUser);
        String url = (USER_PATH + "/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a user is deleted , it return status No Content")
    void whenDeleteUserThenReturnStatusNoContent() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(testUser));
        String url = (USER_PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a user is deleted , it return status No Found")
    void whenDeleteUserThenReturnStatusNoFound() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.empty());
        String url = (USER_PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a book is added , it return status Created")
    void whenAddBookThenReturnStatusCreated() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(testUser));
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(testBook));
        String url = (USER_PATH + "/1/add-books/1");
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a book is added and its exists , it return status Conflict")
    void whenAddBookThenReturnStatusConflict() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(twoTestUser));
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(testBook));
        String url = (USER_PATH + "/1/add-books/1");
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("test, When a book is remove , it return status No Content")
    void whenRemoveBookThenReturnStatusNoContent() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(twoTestUser));
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(testBook));
        String url = (USER_PATH + "/1/remove-books/1");
        mvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("test, When find authenticated user , it return status OK")
    void whenFindAuthenticatedUserThenRetunrStatusOK() throws Exception {
        given(iAuthenticationFacede.getAutentication()).willReturn(authentication);
        String url = (USER_PATH + "/auth-username");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("test, When find user by birthdate between two date, it return status OK")
    void whenFindUserBetweenBirthdateThenReturnStatusOK() throws Exception {
        LocalDate starDate = LocalDate.of(2017, 9, 24);
        LocalDate endDate = LocalDate.of(2020, 9, 24);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> users = new PageImpl<>(testUsers);
        given(mockUsersRepository.findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(starDate, endDate, "miguel", pageable)).willReturn(users);
        String url = (USER_PATH + "?startDate=2017-09-24&endDate=2020-09-24&name=miguel");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("test, When a password is update , it return status OK")
    void whenPasswordIsUpdateThenReturnStatusOK() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.of(testUser));
        String json = new ObjectMapper().writeValueAsString(testUser);
        String url = (USER_PATH + "/password/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("test, When a password is update , it return status No Found")
    void whenPasswordIsUpdateThenReturnStatusNoFound() throws Exception {
        given(mockUsersRepository.findById(1L)).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(testUser);
        String url = (USER_PATH + "/password/1");
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
