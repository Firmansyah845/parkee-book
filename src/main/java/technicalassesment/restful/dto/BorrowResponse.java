package technicalassesment.restful.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowResponse {
    private Long id;
    private String bookTitle;
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private int lateDays;
}