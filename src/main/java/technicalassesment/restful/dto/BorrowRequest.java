package technicalassesment.restful.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowRequest {
    private Long bookId;
    private Long borrowerId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
}
