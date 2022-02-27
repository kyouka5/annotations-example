package service;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonSerializerTest {
    private JsonSerializer jsonSerializer;
    private Book book;

    @BeforeEach
    public void setUp() {
        jsonSerializer = new JsonSerializer();
        book = new Book("Terry Pratchett", "The Colour of Magic", 1983, List.of("fantasy", "comedy"));
    }

    @Test
    public void testSerialize() {
        var serializedObject = jsonSerializer.serialize(book);
        assertTrue(serializedObject.contains("author"));
        assertTrue(serializedObject.contains("year"));
        assertTrue(serializedObject.contains("title"));
        assertFalse(serializedObject.contains("genres"));
        System.out.println(serializedObject);
    }
}
