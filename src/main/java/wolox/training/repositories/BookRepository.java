package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * Method to search book by publisher or genre or year
     *
     * @param publisher variable to search object
     * @param genre     variable to search object
     * @param year      variable to search object
     * @return return a book with specified parameters
     */
    public List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);

    /**
     * Method to search book by publisher or genre or year
     *
     * @param publisher variable to search object
     * @param genre     variable to search object
     * @param year      variable to search object
     * @return return a book with specified parameters
     */
    @Query("SELECT b from Book b "
            + " WHERE (b.publisher = :publisher OR :publisher is null)"
            + " AND (b.genre = :genre OR :genre is null)"
            + " AND (b.year = :year OR :year is null)")
    Optional<List<Book>> findAllByPublisherAndGenreAndYearQuery(
            @Param("publisher") String publisher,
            @Param("genre") String genre,
            @Param("year") String year);
}
