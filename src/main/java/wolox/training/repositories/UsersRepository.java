package wolox.training.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Users repository for persistence
 *
 * @author luismiguelrodriguez
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Method to search users by username
     *
     * @param username variable to search object
     * @return return a user with specified parameter
     */
    public Optional<User> findByUsername(String username);

    /**
     * Method to search users by startDate or endDate or name
     *
     * @param startDate variable to search object
     * @param endDate   variable to search object
     * @param name      variable to search object
     * @return return a user with specified parameters
     */
    Page<User> findAllByBirthdateBetweenAndNameContainingIgnoreCase(
            LocalDate startDate,
            LocalDate endDate,
            String name,
            Pageable pageable);

    /**
     * Method to search users by startDate or endDate or name
     *
     * @param startDate variable to search object
     * @param endDate   variable to search object
     * @param name      variable to search object
     * @return return a user with specified parameters
     */
    @Query("SELECT u FROM User u"
            + " WHERE ( u.birthdate >= :startDate OR cast(:startDate as date) is null)"
            + " OR ( u.birthdate <= :endDate OR cast(:endDate as date) is null)"
            + " OR (:name = '' OR UPPER(u.name) LIKE UPPER(:name))")
    Page<User> findByBirthdateBetweenAndNameContainingIgnoreCaseQuery(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("name") String name,
            Pageable pageable);
}
