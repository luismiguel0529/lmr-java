package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exception.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.dto.BookDTO;
import wolox.training.repositories.BookRepository;
import wolox.training.service.OpenLibraryService;

import java.util.List;
import java.util.Optional;

/**
 * Book controller containing the operations of update , find , delete , find by id and create
 *
 * @author luismiguelrodriguez
 */
@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    /**
     * Repository of books
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Service External Api
     */
    @Autowired
    private OpenLibraryService openLibraryService;

    /**
     * Method for find all elements
     *
     * @return returns all elements in BD
     */
    @ApiOperation(value = "Method to find all books", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfuly retrieved books"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Method for search elements
     *
     * @param id variable used to identify the element to search
     * @return method that returns an object according to the id parameter
     */
    @ApiOperation(value = "Method to find a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfuly retrieved book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book findById(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    /**
     * Method for create elements
     *
     * @param book Object required to save a book
     * @return return a view of the saved object
     */
    @ApiOperation(value = "Method to create a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfuly created book")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * Method for update element
     *
     * @param book Object required to update a book
     * @param id   variable used to identify the element to update
     * @return return a view of the updated object
     */
    @ApiOperation(value = "Method to update a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfuly updated book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book update(@RequestBody Book book, @PathVariable Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setId(id);
        return bookRepository.save(book);
    }

    /**
     * Method for delete element
     *
     * @param id variable used to identify the element to delete
     */
    @ApiOperation(value = "Method to delete a book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfuly deleted book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    /**
     * Method to search a book by isbn
     *
     * @param isbn param to search book in external api or internal repository
     * @return
     */
    @ApiOperation(value = "Method to search a book by isbn", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found successfully"),
            @ApiResponse(code = 201, message = "Book created"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/find-by-isbn")
    public ResponseEntity<Book> findByIsbn(@RequestParam String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> {
                    BookDTO bookDTO = openLibraryService.findInfoBook(isbn);
                    Book book = bookRepository.save(bookDTO.setBook());
                    return new ResponseEntity<>(book, HttpStatus.CREATED);
                });
    }

    /**
     * Method to search a book by the following variables
     *
     * @param publisher variable used to create the filter
     * @param genre     variable used to create the filter
     * @param year      variable used to create the filter
     * @return
     */
    @ApiOperation(value = "Method to search a book by (publisher,genre and year)", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found successfully"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{publisher}/{genre}/{year}")
    public ResponseEntity<List<Book>> findByPublisherGenreYear(
            @PathVariable String publisher,
            @PathVariable String genre,
            @PathVariable String year) {

        List<Book> book = bookRepository
                .findByPublisherAndGenreAndYear(publisher, genre, year)
                .orElseThrow(BookNotFoundException::new);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }


}
