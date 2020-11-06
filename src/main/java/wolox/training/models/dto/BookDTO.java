package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import wolox.training.models.Book;

import java.util.HashMap;
import java.util.List;

@Data
public class BookDTO {

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

    public static BookDTO setBookDto(ObjectNode node, String isbn) {
        final String isbnQuery = "ISBN:" + isbn;
        ObjectMapper mapper = new ObjectMapper();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(isbn);
        bookDTO.setTitle(mapper.convertValue(node.get(isbnQuery).get("title"), String.class));
        bookDTO.setSubtitle(mapper.convertValue(node.get(isbnQuery).get("subtitle"), String.class));
        bookDTO.setPublishers(mapper.convertValue(node.get(isbnQuery).get("publishers"), List.class));
        bookDTO.setPublishDate(mapper.convertValue(node.get(isbnQuery).get("publish_date"), String.class));
        bookDTO.setNumberOfPages(mapper.convertValue(node.get(isbnQuery).get("number_of_pages"), String.class));
        bookDTO.setAuthors(mapper.convertValue(node.get(isbnQuery).get("authors"), List.class));
        return bookDTO;
    }
}
