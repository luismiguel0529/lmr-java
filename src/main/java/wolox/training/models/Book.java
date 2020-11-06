package wolox.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Book model
 *
 * @author luismiguelrodriguez
 */
@Entity
@Data
@ApiModel(description = "Book Model")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SQ")
    @SequenceGenerator(name = "BOOK_SQ", sequenceName = "BOOK_SQ")
    private Long id;

    @ApiModelProperty(notes = "Genre of book")
    private String genre;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Author of book", required = true)
    private String author;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Image of book", required = true)
    private String image;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Title of book", required = true)
    private String title;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Subtitle of book", required = true)
    private String subtitle;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Publisher of book", required = true)
    private String publisher;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Year of book", required = true)
    private String year;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Pages of book", required = true)
    private String pages;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "Isbn of book", required = true)
    private String isbn;

    @JsonIgnore
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author, "Author field is required");
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image, "Image field is required");
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, "Title field is required");
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle, "Subtitle field is required");
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher, "Publisher field is required");
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year, "Year field is required");
        Preconditions.checkArgument(Integer.parseInt(year) <= LocalDate.now().getYear());
        this.year = year;
    }

    public void setPages(String pages) {
        Preconditions.checkNotNull(pages, "Pages field is required");
        Preconditions.checkArgument(Integer.parseInt(pages) > 0, "Invalid number of pages, must be greater than zero");
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, "Isbn field is required");
        this.isbn = isbn;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

}
