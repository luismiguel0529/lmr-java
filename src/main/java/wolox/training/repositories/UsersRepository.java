package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

import java.util.Optional;

/**
 * Users repository for persistence
 *
 * @author luismiguelrodriguez
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByUsername(String username);
}
