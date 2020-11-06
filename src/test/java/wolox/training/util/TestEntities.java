package wolox.training.util;

import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.models.dto.BookDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestEntities {

    public static User mockOneUser() {
        User testUser = new User();
        testUser.setUsername("luismiguel");
        testUser.setName("miguel");
        testUser.setBirthdate(LocalDate.of(2018, 11, 23));
        testUser.setPassword("123");
        return testUser;
    }

    public static User mockUserToPersis() {
        User twoTestUser = new User();
        twoTestUser.setUsername("luismiguel");
        twoTestUser.setName("Luis Miguel");
        twoTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        return twoTestUser;
    }

    public static Book mockBook() {
        Book testBook = new Book();
        testBook.setGenre("genre");
        testBook.setAuthor("author");
        testBook.setImage("image");
        testBook.setTitle("title");
        testBook.setSubtitle("subtitle");
        testBook.setPublisher("publisher");
        testBook.setYear("22");
        testBook.setPages("22");
        testBook.setIsbn("22");
        return testBook;
    }

    public static Book mockBookToPersis() {
        Book twoTestBook = new Book();
        twoTestBook.setGenre("genre");
        twoTestBook.setAuthor("author");
        twoTestBook.setImage("image");
        twoTestBook.setTitle("title");
        twoTestBook.setSubtitle("subtitle");
        twoTestBook.setPublisher("publisher");
        twoTestBook.setYear("222");
        twoTestBook.setPages("22");
        twoTestBook.setIsbn("22");
        return twoTestBook;
    }

    public static List<User> mockManyUsers() {
        List<User> manyTestUsers = new ArrayList<>();
        manyTestUsers.add(mockOneUser());
        return manyTestUsers;
    }

    public static List<Book> mockManyBooks() {
        List<Book> manyTestBooks = new ArrayList<>();
        manyTestBooks.add(mockBook());
        return manyTestBooks;
    }

    public static User mockTwoUser() {
        User twoTestUser = new User();
        twoTestUser.setUsername("luismiguel");
        twoTestUser.setName("Luis Miguel");
        twoTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        return twoTestUser;
    }

    public static BookDTO mockBookDTO() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "name");
        List<HashMap<String, String>> mockListHash = new ArrayList<>();
        mockListHash.add(hashMap);
        BookDTO bookDTO = BookDTO
                .builder()
                .isbn("ISBN")
                .title("title")
                .subtitle("Subtitle")
                .publishers(mockListHash)
                .publishDate("2020")
                .numberOfPages("1")
                .authors(mockListHash)
                .build();
        return bookDTO;
    }
}
