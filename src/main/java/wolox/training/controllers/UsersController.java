package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exception.UsersNotFoundException;
import wolox.training.models.Users;
import wolox.training.repositories.UsersRepository;

/**
 * Users controller containing the operations of update , find , delete , find by id and create
 *
 * @author luismiguelrodriguez
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {

    /**
     * Repository of Users
     */
    @Autowired
    private UsersRepository usersRepository;

    /**
     * Method for find all elements
     *
     * @return returns all elements in BD
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable findAll() {
        return usersRepository.findAll();
    }

    /**
     * Method for search elements
     *
     * @param id variable used to identify the element to search
     * @return method that returns an object according to the id parameter
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users findById(@PathVariable Long id) {
        return usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
    }

    /**
     * Method for create elements
     *
     * @param user Object required to save a user
     * @return return a view of the saved object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
        return usersRepository.save(user);
    }

    /**
     * Method for update element
     *
     * @param user Object required to update a user
     * @param id variable used to identify the element to update
     * @return return a view of the updated object
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Users update(@RequestBody Users user, @PathVariable Long id) {
        usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        return usersRepository.save(user);
    }

    /**
     * Method for delete element
     *
     * @param id variable used to identify the element to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        usersRepository.deleteById(id);
    }
}
