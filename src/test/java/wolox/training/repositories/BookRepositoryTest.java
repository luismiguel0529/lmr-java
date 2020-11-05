package wolox.training.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.models.Book;
import wolox.training.util.TestEntities;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static Book testBook;
    private static Book twoTestBook;
    private static List<Book> testBooks;

    @BeforeAll
    static void setUp() {
        testBook = TestEntities.mockBook();
        testBooks = TestEntities.mockManyBooks();
        twoTestBook = TestEntities.mockBookToPersis();
    }

    @Test
    void whenCallFindAllBookThenReturnAllList() {
        bookRepository.saveAll(testBooks);
        List<Book> bookList = bookRepository.findAll();
        assertEquals(bookList.get(0).getAuthor(), testBooks.get(0).getAuthor());
        assertEquals(bookList.get(0).getId(), testBooks.get(0).getId());
    }

    @Test
    void whenUpdateBookThenReturnBookUpdated() {
        bookRepository.save(testBook);
        testBook.setAuthor("miguel");
        Book book = bookRepository.save(testBook);
        assertEquals(book.getAuthor(), testBook.getAuthor());
    }

    @Test
    void whenSaveBookThenJpaPersisted() {
        bookRepository.save(twoTestBook);
        Optional<Book> book = bookRepository.findById(twoTestBook.getId());
        assertEquals(book.get().getId(), twoTestBook.getId());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearThenReturnListBook() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("author"));
        bookRepository.save(testBook);
        Page<Book> books = bookRepository.findByPublisherAndGenreAndYear(testBook.getPublisher(), testBook.getGenre(), testBook.getYear(), pageable);
        assertEquals(books.getContent().iterator().next().getPublisher(), testBook.getPublisher());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearQueryThenReturnListBook() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("author"));
        bookRepository.save(testBook);
        Page<Book> books = bookRepository.findAllByPublisherAndGenreAndYearQuery(testBook.getPublisher(), testBook.getGenre(), testBook.getYear(), pageable);
        assertEquals(books.getContent().iterator().next().getPublisher(), testBook.getPublisher());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearQueryAndParameterNullThenReturnListBook() {
        Pageable pageable = PageRequest.of(0, 1);
        bookRepository.save(testBook);
        Page<Book> books = bookRepository.findAllByPublisherAndGenreAndYearQuery(null, null, null,null);
        assertEquals(books.getContent().iterator().next().getPublisher(), testBook.getPublisher());
    }

    @Test
    void whenCallfindByAllParametersWithSomeParametersEmptyThenRetunrAListBook(){
        bookRepository.save(testBook);
        Page<Book> books = bookRepository.findByAllParameters("","","","","","",testBook.getPublisher(),"20","24",testBook.getPages(),testBook.getIsbn(),null);
        assertEquals(books.getContent().iterator().next().getAuthor(),testBook.getAuthor());
    }

    @Test
    void whenCallfindByAllParametersWithAllParametersEmptyThenRetunrAListBook(){
        bookRepository.save(testBook);
        Page<Book> books = bookRepository.findByAllParameters("","","","","","","","","","","",null);
        assertEquals(books.getContent().iterator().next().getAuthor(),testBook.getAuthor());
    }

}
