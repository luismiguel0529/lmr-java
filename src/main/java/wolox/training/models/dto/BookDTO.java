package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Data;
import wolox.training.models.Book;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
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
        return BookDTO
                .builder()
                .isbn(isbn)
                .title(mapper.convertValue(node.get(isbnQuery).get("title"), String.class))
                .subtitle(mapper.convertValue(node.get(isbnQuery).get("subtitle"), String.class))
                .publishers(mapper.convertValue(node.get(isbnQuery).get("publishers"), List.class))
                .publishDate(mapper.convertValue(node.get(isbnQuery).get("publish_date"), String.class))
                .numberOfPages(mapper.convertValue(node.get(isbnQuery).get("number_of_pages"), String.class))
                .authors(mapper.convertValue(node.get(isbnQuery).get("authors"), List.class))
                .build();
    }
}
