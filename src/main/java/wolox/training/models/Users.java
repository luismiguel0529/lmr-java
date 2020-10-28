package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import wolox.training.exception.BookAlreadyOwnedException;
import wolox.training.exception.BookNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Users model
 *
 * @author luismiguelrodriguez
 */
@Entity
@ApiModel(description = "Users Model")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Username of user", required = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Name of user", required = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Birthday date of user", required = true)
    private LocalDate birthdate;

    @NotNull
    @ApiModelProperty(notes = "Books of a user", required = true)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Users() {
    }

    public Users(Long id, String username, String name, LocalDate birthdate, List<Book> books) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Add a book to book collection
     *
     * @param book Object to add
     */
    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        } else {
            this.books.add(book);
        }
    }

    /**
     * Remove book to book collection
     *
     * @param book Object to delete
     */
    public void removeBook(Book book) {
        if (books.contains(book)) {
            this.books.remove(book);
        } else {
            throw new BookNotFoundException();
        }
    }
}

