package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

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
    Page<Book> findByPublisherAndGenreAndYear(
            String publisher,
            String genre,
            String year,
            Pageable pageable);


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
    Page<Book> findAllByPublisherAndGenreAndYearQuery(
            @Param("publisher") String publisher,
            @Param("genre") String genre,
            @Param("year") String year,
            Pageable pageable);

    /**
     * Method to search book by any of the following parameters
     *
     * @param id variable to search object
     * @param genre variable to search object
     * @param author variable to search object
     * @param image variable to search object
     * @param title variable to search object
     * @param subtitle variable to search object
     * @param publisher variable to search object
     * @param startYear variable to search object
     * @param endYear variable to search object
     * @param pages variable to search object
     * @param isbn variable to search object
     * @return return a book with specified parameters
     */
    @Query("SELECT b FROM Book b "
            + "WHERE  (b.id = CAST(:id as long) OR :id = '')"
            + "AND (UPPER(b.genre) LIKE UPPER(:genre) OR :genre = '') "
            + "AND (UPPER(b.author) LIKE UPPER(:author) OR :author = '') "
            + "AND (UPPER(b.image) LIKE UPPER(:image) OR :image = '') "
            + "AND (UPPER(b.title) LIKE UPPER(:title) OR :title = '') "
            + "AND (UPPER(b.subtitle) LIKE UPPER(:subtitle) OR :subtitle = '') "
            + "AND (UPPER(b.publisher) LIKE UPPER(:publisher) OR :publisher = '') "
            + "AND  ((b.year BETWEEN :startYear AND :endYear) "
            + "       OR ( b.year >= :startYear AND :endYear = '') "
            + "       OR ( b.year <= :endYear AND :startYear = '')) "
            + "AND (b.pages = :pages  OR :pages = '' ) "
            + "AND (UPPER(b.isbn) LIKE UPPER(:isbn) OR :isbn = '') ")
    Page<Book> findByAllParameters(
            @Param("id") String id,
            @Param("genre") String genre,
            @Param("author") String author,
            @Param("image") String image,
            @Param("title") String title,
            @Param("subtitle") String subtitle,
            @Param("publisher") String publisher,
            @Param("startYear") String startYear,
            @Param("endYear") String endYear,
            @Param("pages") String pages,
            @Param("isbn") String isbn,
            Pageable pageable);
}
