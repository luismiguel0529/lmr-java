package wolox.training.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.dto.BookDTO;
import wolox.training.repositories.BookRepository;
import wolox.training.security.CustomAuthenticationProvider;
import wolox.training.service.OpenLibraryService;
import wolox.training.util.TestEntities;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;

    @MockBean
    private CustomAuthenticationProvider customAuthenticationProvider;

    @MockBean
    private OpenLibraryService openLibraryService;

    private static Book oneTestBook;
    private static List<Book> manyTestBooks;
    private static BookDTO oneTestBookDTO;
    private static final String USER_PATH = "/api/books";

    @BeforeAll
    static void setUp() {
        manyTestBooks = TestEntities.mockManyBooks();
        oneTestBook = TestEntities.mockBook();
        oneTestBookDTO = TestEntities.mockBookDTO();
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test find all book ,return status OK")
    void whenFindBookByIdThenReturnStatusOK() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(oneTestBook));
        String url = (USER_PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a book is searched for its id,it return status not found")
    void whenBookThatNotExistsThenReturnNotFound() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.empty());
        String url = (USER_PATH + "/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test,When a books is searched ,it return status OK")
    void whenFindAllBookThenReturnStatusOK() throws Exception {
        given(mockBookRepository.findAll()).willReturn(manyTestBooks);
        String url = USER_PATH;
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test , When a book is created , it return status Created")
    void whenCreateBookThenReturnStatusCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(oneTestBook);
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
    @DisplayName("Test, When a book is updated , it return status OK")
    void whenUpdateBookThenReturnStatusCreated() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(oneTestBook));
        String json = new ObjectMapper().writeValueAsString(oneTestBook);
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
    @DisplayName("Test, When a book is updated , it return status No Found")
    void whenUpdateBookThenReturnStatusNoFound() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.empty());
        String json = new ObjectMapper().writeValueAsString(oneTestBook);
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
    @DisplayName("Test, When a book is deleted , it return status No Content")
    void whenDeleteBookThenReturnStatusNoContent() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.of(oneTestBook));
        String url = (USER_PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When a book is deleted , it return status No Found")
    void whenDeleteBookThenReturnStatusNoFound() throws Exception {
        given(mockBookRepository.findById(1L)).willReturn(Optional.empty());
        String url = (USER_PATH + "/1");
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When find a book by isbn , it retunr status OK")
    void whenFindBookByIsbnThenRetunrStatusOK() throws Exception {
        given(mockBookRepository.findByIsbn(anyString())).willReturn(Optional.of(oneTestBook));
        String url = (USER_PATH + "/find-by-isbn?isbn=22");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test, When find a book by isbn , it retunr status Created")
    void whenFindBookByIsbnThenRetunrStatusCreated() throws Exception {
        given(mockBookRepository.findByIsbn(anyString())).willReturn(Optional.empty());
        given(openLibraryService.findInfoBook(anyString())).willReturn((oneTestBookDTO));
        String url = (USER_PATH + "/find-by-isbn?isbn=22");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test , When a book is seached by publisher , genre and year ,it return status OK")
    void whenFindByPublisherGenreAndYearThenReturnStatusOK() throws Exception {
        given(mockBookRepository.findAllByPublisherAndGenreAndYearQuery(oneTestBook.getPublisher(), oneTestBook.getGenre(), oneTestBook.getYear())).willReturn(manyTestBooks);
        String url = (USER_PATH + "/findby?publisher=publisher&genre=genre&year=22");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "miguel")
    @Test
    @DisplayName("Test , When a book is seached by many parameters ,it return status OK")
    void whenFindByAllParametersThenReturnStatusOK() throws Exception {
        given(mockBookRepository
                .findByAllParameters(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).willReturn(manyTestBooks);
        String url = (USER_PATH + "/parameters?genre=genre&author=author&image=image&title=title&subtitle=subtitle&publisher=publisher&endYear=2019&startYear=10&pages=22&isbn=22&id=1");
        String json = new ObjectMapper().writeValueAsString(oneTestBook);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
