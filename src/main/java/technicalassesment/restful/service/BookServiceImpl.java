package technicalassesment.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicalassesment.restful.dto.BookRequest;
import technicalassesment.restful.entity.Book;
import technicalassesment.restful.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setStock(request.getStock());
        return bookRepository.save(book);
    }
}
