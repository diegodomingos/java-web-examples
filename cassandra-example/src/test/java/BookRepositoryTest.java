import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BookRepositoryTest {
    private BookRepository repository;
    private Session session;

    @Before
    public void connect(){
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", 9042);
        this.session = connector.getSession();
        repository = new BookRepository(session);
    }

    @Test
    public void whenCreatingAKeyspace_thenCreated() {
        repository.createKeyspaceLibrary();

        ResultSet rs = session.execute("SELECT * FROM system_schema.keyspaces;");

        List<String> matchedKeyspaces = rs.all().stream()
                .filter(r -> r.getString(0).equals(BookRepository.KEYSPACE_NAME.toLowerCase()))
                .map(r -> r.getString(0))
                .collect(Collectors.toList());

        assertEquals(matchedKeyspaces.size(), 1);
        assertTrue(matchedKeyspaces.get(0).equals(BookRepository.KEYSPACE_NAME.toLowerCase()));
    }

    @Test
    public void whenCreatingATable_thenCreated() {
        repository.createTableBook();

        ResultSet rs = session.execute("SELECT * FROM " + BookRepository.KEYSPACE_NAME +
                "." + BookRepository.TABLE_NAME + ";");

        List<String> columnNames = rs.getColumnDefinitions().asList().stream()
                .map(cl -> cl.getName())
                .collect(Collectors.toList());

        assertEquals(columnNames.size(), 3);
        assertTrue(columnNames.contains("id"));
        assertTrue(columnNames.contains("title"));
        assertTrue(columnNames.contains("subject"));
    }

    @Test
    public void whenAddingByTitle_thenBookExists() {
        repository.deleteTable(BookRepository.TABLE_NAME_BY_TITLE);
        repository.createTableBookByTitle();

        String title = "Effective Java";
        Book book = new Book(UUIDs.timeBased(), title, "Programming");
        repository.insertBookByTitle(book);

        Book savedBook = repository.selectByTitle(title);
        assertEquals(book.getTitle(), savedBook.getTitle());
    }

    @Test
    public void whenSelectingAll_thenReturnAllRecords() {
        repository.deleteTable(BookRepository.TABLE_NAME);
        repository.createTableBook();

        Book book = new Book(UUIDs.timeBased(), "Effective Java", "Programming");
        repository.insertBook(book);

        book = new Book(UUIDs.timeBased(), "Clean Code", "Programming");
        repository.insertBook(book);

        List<Book> books = repository.selectAll();

        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Clean Code")));
    }

    @Test
    public void whenAddingANewBookBatch_ThenBookAddedInAllTables() {
        repository.deleteTable(BookRepository.TABLE_NAME);
        repository.deleteTable(BookRepository.TABLE_NAME_BY_TITLE);
        repository.createTableBook();
        repository.createTableBookByTitle();

        String title = "Effective Java";
        Book book = new Book(UUIDs.timeBased(), title, "Programming");
        repository.insertBookBatch(book);

        List<Book> books = repository.selectAll();

        assertEquals(1, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));

        List<Book> booksByTitle = repository.selectAllBookByTitle();

        assertEquals(1, booksByTitle.size());
        assertTrue(booksByTitle.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
    }

    @Test
    public void whenDeletingABookByTitle_thenBookIsDeleted() {
        repository.deleteTable(BookRepository.TABLE_NAME_BY_TITLE);
        repository.createTableBookByTitle();

        Book book = new Book(UUIDs.timeBased(), "Effective Java", "Programming");
        repository.insertBookByTitle(book);

        book = new Book(UUIDs.timeBased(), "Clean Code", "Programming");
        repository.insertBookByTitle(book);

        repository.deletebookByTitle("Clean Code");

        List<Book> books = repository.selectAllBookByTitle();
        assertEquals(1, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
        assertFalse(books.stream().anyMatch(b -> b.getTitle().equals("Clean Code")));

    }
}
