package technicalassesment.restful.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technicalassesment.restful.dto.BorrowRequest;
import technicalassesment.restful.dto.BorrowResponse;
import technicalassesment.restful.entity.Book;
import technicalassesment.restful.entity.BorrowRecord;
import technicalassesment.restful.entity.Borrower;
import technicalassesment.restful.repository.BookRepository;
import technicalassesment.restful.repository.BorrowRecordRepository;
import technicalassesment.restful.repository.BorrowerRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowRecordServiceTest {
    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowRecordServiceImpl borrowRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {
        // Arrange
        BorrowRequest request = new BorrowRequest();
        request.setBookId(1L);
        request.setBorrowerId(1L);
        request.setBorrowDate(LocalDate.now());
        request.setDueDate(LocalDate.now().plusDays(7));

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setStock(5);

        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Jane Doe");

        BorrowRecord mockBorrowRecord = new BorrowRecord();
        mockBorrowRecord.setId(1L);
        mockBorrowRecord.setBook(book);
        mockBorrowRecord.setBorrower(borrower);

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(java.util.Optional.of(borrower));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(mockBorrowRecord);

        // Act
        BorrowRecord borrowRecord = borrowRecordService.borrowBook(request);

        // Assert
        assertNotNull(borrowRecord);
        assertEquals("Test Book", borrowRecord.getBook().getTitle());
        assertEquals("Jane Doe", borrowRecord.getBorrower().getName());
        verify(bookRepository, times(1)).save(book);
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
    }

    @Test
    void testReturnBook() {
        // Arrange
        Long borrowRecordId = 1L;
        String returnDate = "2025-01-18";

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setStock(4);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(borrowRecordId);
        borrowRecord.setBook(book);
        borrowRecord.setReturned(false);

        when(borrowRecordRepository.findById(borrowRecordId)).thenReturn(java.util.Optional.of(borrowRecord));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        BorrowRecord returnedBorrowRecord = borrowRecordService.returnBook(borrowRecordId, returnDate);

        // Assert
        assertTrue(returnedBorrowRecord.isReturned());
        assertEquals(LocalDate.parse(returnDate), returnedBorrowRecord.getReturnDate());
        verify(bookRepository, times(1)).save(book);
        verify(borrowRecordRepository, times(1)).save(borrowRecord);
    }

    @Test
    void testGetAllBorrowRecords() {
        // Arrange
        BorrowRecord record1 = new BorrowRecord();
        record1.setId(1L);
        record1.setBook(new Book(1L, "Book 1", "test", 5));
        record1.setBorrower(new Borrower(1L, "Borrower 1", "test"));
        record1.setBorrowDate(LocalDate.now().minusDays(10));
        record1.setDueDate(LocalDate.now().minusDays(5));
        record1.setReturned(false);

        BorrowRecord record2 = new BorrowRecord();
        record2.setId(2L);
        record2.setBook(new Book(2L, "Book 2", "test2", 3));
        record2.setBorrower(new Borrower(2L, "Borrower 2", "test2"));
        record2.setBorrowDate(LocalDate.now().minusDays(15));
        record2.setDueDate(LocalDate.now().minusDays(10));
        record2.setReturned(true);

        when(borrowRecordRepository.findAll()).thenReturn(List.of(record1, record2));

        // Act
        List<BorrowResponse> responses = borrowRecordService.getAllBorrowRecords();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        BorrowResponse response1 = responses.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("Book 1", response1.getBookTitle());
        assertEquals("Borrower 1", response1.getBorrowerName());
        assertFalse(response1.isReturned());

        BorrowResponse response2 = responses.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Book 2", response2.getBookTitle());
        assertEquals("Borrower 2", response2.getBorrowerName());
        assertTrue(response2.isReturned());

        verify(borrowRecordRepository, times(1)).findAll();
    }

    @Test
    void testGetOverdueBorrowRecords() {
        // Arrange
        BorrowRecord overdueRecord = new BorrowRecord();
        overdueRecord.setId(1L);
        overdueRecord.setBook(new Book(1L, "Overdue Book", "test3", 2));
        overdueRecord.setBorrower(new Borrower(1L, "Overdue Borrower", "test3"));
        overdueRecord.setBorrowDate(LocalDate.now().minusDays(20));
        overdueRecord.setDueDate(LocalDate.now().minusDays(10));
        overdueRecord.setReturned(false);

        when(borrowRecordRepository.findByReturnedFalseAndDueDateBefore(LocalDate.now()))
                .thenReturn(List.of(overdueRecord));

        // Act
        List<BorrowResponse> responses = borrowRecordService.getOverdueBorrowRecords();

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());

        BorrowResponse response = responses.get(0);
        assertEquals(1L, response.getId());
        assertEquals("Overdue Book", response.getBookTitle());
        assertEquals("Overdue Borrower", response.getBorrowerName());
        assertFalse(response.isReturned());

        verify(borrowRecordRepository, times(1))
                .findByReturnedFalseAndDueDateBefore(LocalDate.now());
    }
}