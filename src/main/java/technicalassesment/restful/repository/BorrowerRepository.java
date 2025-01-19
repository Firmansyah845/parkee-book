package technicalassesment.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technicalassesment.restful.entity.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
