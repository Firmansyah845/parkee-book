package technicalassesment.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technicalassesment.restful.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
