package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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

    List<User> findAllByBirthdateBetweenAndNameContainingIgnoreCase(
            LocalDate startDate,
            LocalDate endDate,
            String name);
}
