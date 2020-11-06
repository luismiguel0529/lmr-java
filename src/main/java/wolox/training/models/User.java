package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import wolox.training.exception.BookAlreadyOwnedException;
import wolox.training.exception.BookNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Data
@ApiModel(description = "Users Model")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SQ")
    @SequenceGenerator(name = "USER_SQ", sequenceName = "USER_SQ")
    private Long id;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Username of user", required = true)
    private String username;

    @ApiModelProperty(notes = "User's password")
    private String password;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Name of user", required = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Birthday date of user", required = true)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthdate;

    @NotNull
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "Books of a user", required = true)
    private List<Book> books = new ArrayList<>();

    @Column(name="user_type", insertable = false, updatable = false)
    @JsonProperty("user_type")
    private String userType;

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, "Username field is required");
        this.username = username;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, "Name field is required");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, "Birthdate field is required");
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Add a book to book collection
     *
     * @param book Object to add
     */
    public void addBook(Book book) {
        Preconditions.checkNotNull(book, "The book data can not be null");
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
        Preconditions.checkNotNull(book, "The book data can not be null");
        if (books.contains(book)) {
            this.books.remove(book);
        } else {
            throw new BookNotFoundException();
        }
    }
}

