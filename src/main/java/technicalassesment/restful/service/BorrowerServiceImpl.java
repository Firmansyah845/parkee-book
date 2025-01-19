package technicalassesment.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technicalassesment.restful.dto.BorrowerRequest;
import technicalassesment.restful.entity.Borrower;
import technicalassesment.restful.repository.BorrowerRepository;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    public Borrower createBorrower(BorrowerRequest request) {
        Borrower borrower = new Borrower();
        borrower.setName(request.getName());
        borrower.setEmail(request.getEmail());
        return borrowerRepository.save(borrower);
    }
}
