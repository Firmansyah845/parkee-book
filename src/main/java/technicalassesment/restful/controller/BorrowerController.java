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
import technicalassesment.restful.dto.BorrowerRequest;
import technicalassesment.restful.entity.Borrower;
import technicalassesment.restful.service.BorrowerService;

@RestController
@RequestMapping("/api/borrowers")
@Tag(name = "Borrower Controller", description = "APIs for managing borrowers")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowerService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Add a new borrower", description = "Create a new borrower in the library system")
    public ResponseEntity<Borrower> createBorrower(@RequestBody BorrowerRequest request) {
        return ResponseEntity.ok(borrowerService.createBorrower(request));
    }
}
