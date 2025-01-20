package technicalassesment.restful.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technicalassesment.restful.dto.BorrowerRequest;
import technicalassesment.restful.entity.Borrower;
import technicalassesment.restful.repository.BorrowerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {
    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBorrower() {
        // Arrange
        BorrowerRequest request = new BorrowerRequest();
        request.setName("Jane Doe");
        request.setEmail("jane.doe@example.com");

        Borrower mockBorrower = new Borrower();
        mockBorrower.setId(1L);
        mockBorrower.setName("Jane Doe");

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(mockBorrower);

        // Act
        Borrower createdBorrower = borrowerService.createBorrower(request);

        // Assert
        assertEquals("Jane Doe", createdBorrower.getName());
        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }
}
