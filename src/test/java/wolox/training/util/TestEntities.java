package wolox.training.util;

import wolox.training.models.Book;
import wolox.training.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestEntities {

    public static User mockOneUser() {
        User oneTestUser = new User();
        oneTestUser.setUsername("luismiguel");
        oneTestUser.setName("Luis Miguel");
        oneTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        oneTestUser.setPassword("123");
        return oneTestUser;
    }

    public static User mockUserToPersis() {
        User twoTestUser = new User();
        twoTestUser.setUsername("luismiguel");
        twoTestUser.setName("Luis Miguel");
        twoTestUser.setBirthdate(LocalDate.of(1993, 11, 23));
        return twoTestUser;
    }

    public static Book mockBook() {
        Book oneTestBook = new Book();
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

}
