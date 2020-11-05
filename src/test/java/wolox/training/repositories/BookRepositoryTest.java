package wolox.training.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    private static Book oneTestBook;
    private static Book twoTestBook;
    private static List<Book> manyTestBooks;

    @BeforeAll
    static void setUp() {
        oneTestBook = TestEntities.mockBook();
        manyTestBooks = TestEntities.mockManyBooks();
        twoTestBook = TestEntities.mockBookToPersis();
    }

    @Test
    void whenCallFindAllBookThenReturnAllList() {
        bookRepository.saveAll(manyTestBooks);
        List<Book> bookList = bookRepository.findAll();
        assertEquals(bookList.get(0).getAuthor(), manyTestBooks.get(0).getAuthor());
        assertEquals(bookList.get(0).getId(), manyTestBooks.get(0).getId());
    }

    @Test
    void whenUpdateBookThenReturnBookUpdated() {
        bookRepository.save(oneTestBook);
        oneTestBook.setAuthor("miguel");
        Book book = bookRepository.save(oneTestBook);
        assertEquals(book.getAuthor(), oneTestBook.getAuthor());
    }

    @Test
    void whenSaveBookThenJpaPersisted() {
        bookRepository.save(twoTestBook);
        Optional<Book> book = bookRepository.findById(twoTestBook.getId());
        assertEquals(book.get().getId(), twoTestBook.getId());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearThenReturnListBook() {
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findByPublisherAndGenreAndYear(oneTestBook.getPublisher(), oneTestBook.getGenre(), oneTestBook.getYear());
        assertEquals(books.get(0).getPublisher(), oneTestBook.getPublisher());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearQueryThenReturnListBook() {
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findAllByPublisherAndGenreAndYearQuery(oneTestBook.getPublisher(), oneTestBook.getGenre(), oneTestBook.getYear());
        assertEquals(books.get(0).getPublisher(), oneTestBook.getPublisher());
    }

    @Test
    void whenCallfindByPublisherAndGenreAndYearQueryAndParameterNullThenReturnListBook() {
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findAllByPublisherAndGenreAndYearQuery(null, null, null);
        assertEquals(books.get(0).getPublisher(), oneTestBook.getPublisher());
    }

    @Test
    void whenCallfindByAllParametersWithSomeParametersEmptyThenRetunrAListBook(){
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findByAllParameters("","","","","","",oneTestBook.getPublisher(),"20","24",oneTestBook.getPages(),oneTestBook.getIsbn());
        assertEquals(books.get(0).getAuthor(),oneTestBook.getAuthor());
    }

    @Test
    void whenCallfindByAllParametersWithAllParametersEmptyThenRetunrAListBook(){
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findByAllParameters("","","","","","","","","","","");
        assertEquals(books.get(0).getAuthor(),oneTestBook.getAuthor());
    }

}
