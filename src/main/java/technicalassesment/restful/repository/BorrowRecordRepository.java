package technicalassesment.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technicalassesment.restful.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByReturnedFalseAndDueDateBefore(java.time.LocalDate date);
}
