package wolox.training.models;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String username;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @Column(nullable = false)
    @NotEmpty
    private LocalDate birthdate;

    @Column(nullable = false)
    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Users() {
    }

    public Users(Long id, @NotEmpty String username, @NotEmpty String name, @NotEmpty LocalDate birthdate, @NotEmpty List<Book> books) {
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

    public void addBook(Book book){
        if (books.contains(book)){
            throw new BookAlreadyOwnedException();
        }else {
            this.books.add(book);
        }
    }

    public void removeBook(Book book){
        if (books.contains(book)){
            this.books.remove(book);
        } else {
            throw new BookNotFoundException();
        }
    }
}

