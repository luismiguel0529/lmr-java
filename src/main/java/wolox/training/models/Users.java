package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.Preconditions;
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

    @JsonManagedReference
    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "Books of a user", required = true)
    private List<Book> books;

    public Users() {
    }

    public Users(Long id, String username, String name, LocalDate birthdate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.birthdate = birthdate;
        this.books = new ArrayList<>();
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
        Preconditions.checkNotNull(username, "Username field is required");
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, "Name field is required");
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, "Birthdate field is required");
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
        Preconditions.checkNotNull(book, "The book data can not be null");
        if (books.contains(book)){
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
        Preconditions.checkNotNull(book, "The book data can not be null");
        if (books.contains(book)) {
            this.books.remove(book);
        } else {
            throw new BookNotFoundException();
        }
    }
}

