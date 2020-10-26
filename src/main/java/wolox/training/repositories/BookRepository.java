package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    public Optional<Book> findByAuthor(String author);


}
