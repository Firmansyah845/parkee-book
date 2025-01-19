package technicalassesment.restful.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import technicalassesment.restful.dto.BookRequest;
import technicalassesment.restful.entity.Book;
import technicalassesment.restful.service.BookService;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Add a new book", description = "Create a new book in the library")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }
}
