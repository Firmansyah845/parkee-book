package technicalassesment.restful.service;

import technicalassesment.restful.dto.BookRequest;
import technicalassesment.restful.entity.Book;

public interface BookService {
    Book createBook(BookRequest request);
}
