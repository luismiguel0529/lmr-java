package wolox.training.util;

import wolox.training.models.Book;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestEntities {

    public static User mockOneUser() {
        User oneTestUser = new User();
        oneTestUser.setId(1l);
        oneTestUser.setUsername("luismiguel");
        oneTestUser.setName("Luis Miguel");
        oneTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        return oneTestUser;
    }

    public static Book mockBook() {
        Book oneTestBook = new Book();
        oneTestBook.setId(1L);
        oneTestBook.setId(1L);
        oneTestBook.setGenre("genre");
        oneTestBook.setAuthor("author");
        oneTestBook.setImage("image");
        oneTestBook.setTitle("title");
        oneTestBook.setSubtitle("subtitle");
        oneTestBook.setPublisher("publisher");
        oneTestBook.setYear("222");
        oneTestBook.setPages("22");
        oneTestBook.setIsbn("22");
        return oneTestBook;
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
        twoTestUser.setId(1l);
        twoTestUser.setUsername("luismiguel");
        twoTestUser.setName("Luis Miguel");
        twoTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        return twoTestUser;
    }

}
