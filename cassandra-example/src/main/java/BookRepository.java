import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public static final String KEYSPACE_NAME = "library";
    public static final String TABLE_NAME = "books";
    public static final String TABLE_NAME_BY_TITLE = TABLE_NAME + "ByTitle";
    private Session session;

    public BookRepository(Session session) {
        this.session = session;
    }

    public void createKeyspaceLibrary() {
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                .append(KEYSPACE_NAME).append(" WITH replication = {")
                .append("'class':'SimpleStrategy','replication_factor':1};");
        final String query = sb.toString();
        session.execute(query);
    }

    public void createTableBook() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME).append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("title text, ")
                .append("subject text);");
        final String query = sb.toString();
        session.execute(query);
    }

    public void createTableBookByTitle() {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME_BY_TITLE).append("(")
                .append("id uuid, ")
                .append("title text, ")
                .append("PRIMARY KEY (title, id));");
        final String query = sb.toString();
        session.execute(query);
    }

    public void insertBook(Book book) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME)
                .append(" (id, title, subject) VALUES (")
                .append(book.getId()).append(", '")
                .append(book.getTitle()).append("', '")
                .append(book.getSubject()).append("');");
        final String query = sb.toString();
        session.execute(query);
    }

    public void insertBookByTitle(Book book) {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME_BY_TITLE)
                .append(" (id, title) VALUES (")
                .append(book.getId()).append(", '")
                .append(book.getTitle()).append("');");
        final String query = sb.toString();
        session.execute(query);
    }

    public void insertBookBatch(Book book) {
        StringBuilder sb = new StringBuilder("BEGIN BATCH ").append("INSERT INTO ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME)
                .append("(id, title, subject) VALUES (")
                .append(book.getId()).append(", '")
                .append(book.getTitle()).append("', '")
                .append(book.getSubject()).append("');")
                .append("INSERT INTO ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME_BY_TITLE)
                .append("(id, title) VALUES (")
                .append(book.getId()).append(", '")
                .append(book.getTitle()).append("');")
                .append("APPLY BATCH;");

        final String query = sb.toString();
        session.execute(query);
    }

    public Book selectByTitle(String title) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ")
                .append(KEYSPACE_NAME).append(".")
                .append(TABLE_NAME_BY_TITLE)
                .append(" WHERE title = '").append(title).append("';");

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Book> books = new ArrayList<Book>();
        rs.forEach(r -> {
            books.add(new Book(r.getUUID("id"), r.getString("title"), null));
        });

        return books.get(0);
    }

    public List<Book> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ")
                .append(KEYSPACE_NAME).append(".").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Book> books = new ArrayList<>();
        rs.forEach(r -> {
            books.add(new Book(r.getUUID("id"), r.getString("title"), r.getString("subject")));
        });
        return books;
    }

    public List<Book> selectAllBookByTitle() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ")
                .append(KEYSPACE_NAME).append(".").append(TABLE_NAME_BY_TITLE);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Book> books = new ArrayList<>();
        rs.forEach(r -> {
            books.add(new Book(r.getUUID("id"), r.getString("title"), null));
        });
        return books;
    }

    public void deletebookByTitle(String title) {
        StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(KEYSPACE_NAME).append(".").append(TABLE_NAME_BY_TITLE)
                .append(" WHERE title = '").append(title).append("';");

        final String query = sb.toString();
        session.execute(query);
    }

    public void deleteTable(String tableName) {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ")
                .append(KEYSPACE_NAME).append(".").append(tableName);

        final String query = sb.toString();
        session.execute(query);
    }

}
