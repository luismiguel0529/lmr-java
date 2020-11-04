package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.List;
import java.util.Optional;

/**
 * Book repository for persistence
 *
 * @author luismiguelrodriguez
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Method to search book by author
     *
     * @param author variable to search object
     * @return return a book with specified parameter
     */
    public Optional<Book> findByAuthor(String author);

    /**
     * Method to search book by isbn
     *
     * @param isbn variable to search object
     * @return return a book with specified parameter
     */
    public Optional<Book> findByIsbn(String isbn);

    /**
     * Method to search book by isbn or genre or year
     *
     * @param publisher variable to search object
     * @param genre     variable to search object
     * @param year      variable to search object
     * @return return a book with specified parameters
     */
    public Optional<List<Book>> findByPublisherAndGenreAndYear(String publisher, String genre, String year);

    @Query("SELECT b from Book b "
            + " WHERE (?1 is null OR b.publisher = ?1)"
            + " AND (?2 is null OR b.genre = ?2)"
            + " AND (?3 is null OR b.year = ?3)")
    Optional<List<Book>> findAllByPublisherAndGenreAndYearQuery(
            String publisher,
            String genre,
            String year);
}
