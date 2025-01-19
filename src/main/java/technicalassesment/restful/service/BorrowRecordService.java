package technicalassesment.restful.service;

import technicalassesment.restful.dto.BorrowRequest;
import technicalassesment.restful.dto.BorrowResponse;
import technicalassesment.restful.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordService {
    BorrowRecord borrowBook(BorrowRequest request);
    BorrowRecord returnBook(Long borrowRecordId, String returnDate);

    List<BorrowResponse> getAllBorrowRecords();
    List<BorrowResponse> getOverdueBorrowRecords();
}
