package technicalassesment.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.stream.Collectors;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    public BorrowRecord borrowBook(BorrowRequest request) {
        // Validate book availability
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getStock() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }

        // Validate borrower
        Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        // Create borrow record
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setBorrower(borrower);
        borrowRecord.setBorrowDate(request.getBorrowDate());
        borrowRecord.setDueDate(request.getDueDate());
        borrowRecord.setReturned(false);

        // Update book stock
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public BorrowRecord returnBook(Long borrowRecordId, String returnDate) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (borrowRecord.isReturned()) {
            throw new RuntimeException("Book has already been returned");
        }

        // Update borrow record
        borrowRecord.setReturnDate(LocalDate.parse(returnDate));
        borrowRecord.setReturned(true);

        // Update book stock
        Book book = borrowRecord.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public List<BorrowResponse> getAllBorrowRecords() {
        return borrowRecordRepository.findAll().stream()
                .map(this::toBorrowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowResponse> getOverdueBorrowRecords() {
        return borrowRecordRepository.findByReturnedFalseAndDueDateBefore(LocalDate.now()).stream()
                .map(this::toBorrowResponse)
                .collect(Collectors.toList());
    }

    private BorrowResponse toBorrowResponse(BorrowRecord record) {
        BorrowResponse response = new BorrowResponse();
        response.setId(record.getId());
        response.setBookTitle(record.getBook().getTitle());
        response.setBorrowerName(record.getBorrower().getName());
        response.setBorrowDate(record.getBorrowDate());
        response.setDueDate(record.getDueDate());
        response.setReturnDate(record.getReturnDate());
        response.setReturned(record.isReturned());
        response.setLateDays(record.getLateDays());
        return response;
    }
}
