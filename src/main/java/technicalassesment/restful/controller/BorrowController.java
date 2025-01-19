package technicalassesment.restful.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import technicalassesment.restful.dto.BorrowRequest;
import technicalassesment.restful.dto.BorrowResponse;
import technicalassesment.restful.entity.BorrowRecord;
import technicalassesment.restful.service.BorrowRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "Borrow Record Controller", description = "APIs for managing borrow records")
public class BorrowController {
    @Autowired
    private BorrowRecordService borrowRecordService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Borrow a book", description = "Create a new borrow record when a book is borrowed")
    public ResponseEntity<BorrowRecord> borrowBook(@RequestBody BorrowRequest request) {
        return ResponseEntity.ok(borrowRecordService.borrowBook(request));
    }

    @PutMapping("/return/{id}")
    @Operation(summary = "Return a book", description = "Update a borrow record when a book is returned")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long id, @RequestParam String returnDate) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id, returnDate));
    }

    @GetMapping("/records")
    @Operation(summary = "Get all borrow records", description = "Retrieve a list of all borrow records")
    public ResponseEntity<List<BorrowResponse>> getAllBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getAllBorrowRecords());
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get over due borrow records", description = "Retrieve a list of all overdue borrow records")
    public ResponseEntity<List<BorrowResponse>> getOverdueBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getOverdueBorrowRecords());
    }
}