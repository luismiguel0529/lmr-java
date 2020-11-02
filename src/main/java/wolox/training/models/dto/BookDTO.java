package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import wolox.training.models.Book;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class BookDTO implements Serializable {

    private String isbn;

    @JsonProperty
    private String title;

    @JsonProperty
    private String subtitle;

    @JsonProperty
    private List<HashMap<String, String>> publishers;

    @JsonProperty(value = "publish_date")
    private String publishDate;

    @JsonProperty(value = "number_of_pages")
    private String numberOfPages;

    @JsonProperty
    private List<HashMap<String, String>> authors;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public List<HashMap<String, String>> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<HashMap<String, String>> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public List<HashMap<String, String>> getAuthors() {
        return authors;
    }

    public void setAuthors(List<HashMap<String, String>> authors) {
        this.authors = authors;
    }

    public Book setBook() {

        Book book = new Book();
        book.setImage("No image");
        book.setIsbn(this.isbn);
        book.setTitle(this.title);
        book.setSubtitle(this.subtitle);
        book.setPublisher(this.publishers.get(0).get("name"));
        book.setPages(this.numberOfPages);
        book.setAuthor(this.authors.get(0).get("name"));
        book.setYear(this.publishDate);

        return book;
    }
}