package technicalassesment.restful.service;

import technicalassesment.restful.dto.BorrowerRequest;
import technicalassesment.restful.entity.Borrower;

public interface BorrowerService {
    Borrower createBorrower(BorrowerRequest request);
}
