package technicalassesment.restful.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technicalassesment.restful.dto.BookRequest;
import technicalassesment.restful.entity.Book;
import technicalassesment.restful.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        request.setAuthor("John Doe");
        request.setStock(5);

        Book mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setTitle("Test Book");
        mockBook.setAuthor("John Doe");

        when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

        // Act
        Book createdBook = bookService.createBook(request);

        // Assert
        assertEquals("Test Book", createdBook.getTitle());
        assertEquals("John Doe", createdBook.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
