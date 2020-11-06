package wolox.training.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import wolox.training.exception.BookNotFoundException;
import wolox.training.models.dto.BookDTO;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest
public class OpenLibraryServiceTest {

    @Autowired
    private OpenLibraryService openLibraryService;

    private WireMockServer wireMockServer;
    private static final String isbnSuccess = "0385472579";
    private static final String isbnFail = "2";

    @BeforeEach
    void setUp() {
        String url = "/api/books?bibkeys=ISBN:%s&format=json&jscmd=data";
        wireMockServer = new WireMockServer(8085);
        wireMockServer.start();

        wireMockServer
                .stubFor(get(urlEqualTo(String.format(url, isbnSuccess)))
                        .willReturn(aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(200)
                                .withBodyFile("**/response.json")));

        wireMockServer
                .stubFor(get(urlEqualTo(String.format(url, isbnFail)))
                        .willReturn(aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(200)
                                .withBodyFile("{}")));
    }

    @Test
    @DisplayName("Test, when search a book ,it returns a book")
    void whenFindBookByIsbnThenReturnBook() {
        BookDTO bookDTO = openLibraryService.findInfoBook(isbnSuccess);
        Assert.assertEquals("0385472579", bookDTO.getIsbn());
        Assert.assertEquals("Zen speaks", bookDTO.getTitle());
        Assert.assertEquals("shouts of nothingness", bookDTO.getSubtitle());
        Assert.assertEquals("1994", bookDTO.getPublishDate());
    }

    @Test
    @DisplayName("Test, when search a book ,it returns not found")
    void whenFIndBookByIsbnThenReturnNotFound() {
        Assertions.assertThrows(BookNotFoundException.class, () -> openLibraryService.findInfoBook(isbnFail));
    }


    @AfterEach
    void setDown() {
        wireMockServer.stop();
    }
}
