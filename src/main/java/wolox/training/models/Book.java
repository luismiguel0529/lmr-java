package wolox.training.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/***
 * Book model
 * @author luismiguelrodriguez
 */
@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String genre;

    @Column(nullable = false)
    @NotEmpty
    private String author;

    @Column(nullable = false)
    @NotEmpty
    private String image;

    @Column(nullable = false)
    @NotEmpty
    private String title;

    @Column(nullable = false)
    @NotEmpty
    private String subtitle;

    @Column(nullable = false)
    @NotEmpty
    private String publisher;

    @Column(nullable = false)
    @NotEmpty
    private String year;

    @Column(nullable = false)
    @NotEmpty
    private String pages;

    @Column(nullable = false)
    @NotEmpty
    private String isbn;

    public Book() {
    }

    public Book(String genre, String author, String image, String title, String subtitle, String publisher, String year, String pages, String isbn) {
        this.genre = genre;
        this.author = author;
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
