package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exception.BookNotFoundException;
import wolox.training.exception.UsersNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UsersRepository;
import wolox.training.security.IAuthenticationFacede;

import java.time.LocalDate;
import java.util.List;

/**
 * Users controller containing the operations of update , find , delete , find by id and create
 *
 * @author luismiguelrodriguez
 */
@RestController
@RequestMapping("/api/users")
@Api
public class UsersController {

    /**
     * Repository of Users
     */
    @Autowired
    private UsersRepository usersRepository;

    /**
     * Repository of Books
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Service  for encoding passwords.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Interface for view user authenticate
     */
    @Autowired
    private IAuthenticationFacede iAuthenticationFacede;

    /**
     * Method for find all elements
     *
     * @return returns all elements in BD
     */
    @ApiOperation(value = "Method to find all users", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfuly retrieved users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    /**
     * Method for search elements
     *
     * @param id variable used to identify the element to search
     * @return method that returns an object according to the id parameter
     */
    @ApiOperation(value = "Method to find a user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfuly retrieved user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable Long id) {
        return usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
    }

    /**
     * Method for create elements
     *
     * @param user Object required to save a user
     * @return return a view of the saved object
     */
    @ApiOperation(value = "Method to create a user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfuly created user")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    /**
     * Method for update element
     *
     * @param user Object required to update a user
     * @param id   variable used to identify the element to update
     * @return return a view of the updated object
     */
    @ApiOperation(value = "Method to update a user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfuly updated user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User user, @PathVariable Long id) {
        usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        user.setId(id);
        return usersRepository.save(user);
    }

    /**
     * Method for delete element
     *
     * @param id variable used to identify the element to delete
     */
    @ApiOperation(value = "Method to delete a user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfuly deleted user"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        usersRepository.deleteById(id);
    }

    /**
     * Method to add a book to a user
     *
     * @param id     User identifier to add a book
     * @param bookid Book identifier to add
     */
    @ApiOperation(value = "Method to add a book to a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book Add"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @PatchMapping("/{id}/add-books/{bookid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@PathVariable Long id, @PathVariable Long bookid) {
        User user = usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        Book book = bookRepository.findById(bookid).orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        usersRepository.save(user);
    }

    /**
     * Method to delete a book to a user
     *
     * @param id     User identifier to delete a book
     * @param bookid Book identifier to delete
     */
    @ApiOperation(value = "Method to delete a book to a user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book deleted"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @PatchMapping("/{id}/remove-books/{bookid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(@PathVariable Long id, @PathVariable Long bookid) {
        User user = usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        Book book = bookRepository.findById(bookid).orElseThrow(BookNotFoundException::new);
        user.removeBook(book);
        usersRepository.save(user);
    }

    /**
     * Method to view a autenticated user
     *
     * @return retunr autenticated user
     */
    @ApiOperation(value = "Method to view a autenticated user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authenticated user")
    })
    @GetMapping("/auth-username")
    public ResponseEntity<Object> authUsername() {
        Authentication authentication = iAuthenticationFacede.getAutentication();
        String response = "{\"Autenticated user\": \"%s\"}";
        return new ResponseEntity<>(String.format(response,authentication.getName()),HttpStatus.OK);
    }

    /**
     * Method to search user with birthdate beetween two date
     *
     * @param startDate initial date
     * @param endDate   end date
     * @param name      name of user en repository
     * @return
     */
    @ApiOperation(value = "Method to search user with birthdate beetween two date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authenticated user")
    })
    @GetMapping("/{startDate}/{endDate}/{name}")
    public ResponseEntity<List<User>> findByBirthdateBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate endDate,
            @PathVariable String name) {
        List<User> books = usersRepository
                .findAllByBirthdateBetweenAndNameContainingIgnoreCase(startDate, endDate, name)
                .orElseThrow(BookNotFoundException::new);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * Method to update  password to a user
     *
     * @param id   User identifier to update a password
     * @param user Object to update password
     */
    @ApiOperation(value = "Method to update  password to a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password update"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PutMapping("/password/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long id, @RequestBody User user) {
        usersRepository.findById(id).orElseThrow(UsersNotFoundException::new);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
}
