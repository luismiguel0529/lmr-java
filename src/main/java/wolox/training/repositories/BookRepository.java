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
     * Method to search book by isbn or genre or year
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

    @Query("SELECT b from Book b "
            + " WHERE (?1 = '' OR b.publisher = ?1)"
            + " AND (?2 = '' OR b.genre = ?2)"
            + " AND (?3 = '' OR b.year = ?3)")
    Page<Book> findAllByPublisherAndGenreAndYearQuery(
            String publisher,
            String genre,
            String year,
            Pageable pageable);

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
