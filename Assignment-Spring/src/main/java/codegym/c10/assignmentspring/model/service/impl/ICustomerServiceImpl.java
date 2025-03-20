package codegym.c10.assignmentspring.model.service.impl;

import codegym.c10.assignmentspring.model.entity.Customer;
import codegym.c10.assignmentspring.model.entity.Transaction;
import codegym.c10.assignmentspring.model.repository.ICustomerRepository;
import codegym.c10.assignmentspring.model.repository.ITransactionRepository;
import codegym.c10.assignmentspring.model.service.ICustomerService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Validated
@Service
public class ICustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;

    private final Validator validator;

    public ICustomerServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Iterable<Customer> findAll() {
        return iCustomerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        validateCustomer(customer);
        iCustomerRepository.save(customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return iCustomerRepository.findById(id);
    }

    @Override
    public void remove(Long id) {

    }

    private void validateCustomer(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Dữ liệu không hợp lệ.");
            throw new ConstraintViolationException(errorMessage, violations);
        }
    }
}
