package wolox.training.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.exception.BookNotFoundException;
import wolox.training.models.dto.BookDTO;

import java.net.URI;
import java.util.List;

@Service
public class OpenLibraryService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${urlExternal}")
    private String urlOpenLibrary;

    public BookDTO findInfoBook(String isbn) {
        final String isbnQuery = "ISBN:" + isbn;
        URI uri = UriComponentsBuilder
                .fromHttpUrl(urlOpenLibrary)
                .path("books")
                .queryParam("bibkeys", isbnQuery)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .build()
                .toUri();

        ObjectNode node = restTemplate.getForObject(uri, ObjectNode.class);

        if (!node.isEmpty()) {
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
        throw new BookNotFoundException();
    }
}
