package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Users repository for persistence
 *
 * @author luismiguelrodriguez
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    Page<User> findAllByBirthdateBetweenAndNameContainingIgnoreCase(
            LocalDate startDate,
            LocalDate endDate,
            String name,
            Pageable pageable);

    @Query("SELECT u FROM User u"
            + " WHERE (cast(?1 as date) is null OR u.birthdate >= ?1)"
            + " AND (cast(?2 as date) is null OR u.birthdate <= ?2)"
            + " AND (?3 = '' OR UPPER(?3) LIKE UPPER(?3))")
    Page<User> findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(
            LocalDate startDate,
            LocalDate endDate,
            String name,
            Pageable pageable);
}
