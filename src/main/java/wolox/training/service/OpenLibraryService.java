package wolox.training.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.exception.BookNotFoundException;
import wolox.training.models.dto.BookDTO;

import java.net.URI;

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
            return BookDTO.setBookDto(node, isbn);
        }

        throw new BookNotFoundException();
    }
}
