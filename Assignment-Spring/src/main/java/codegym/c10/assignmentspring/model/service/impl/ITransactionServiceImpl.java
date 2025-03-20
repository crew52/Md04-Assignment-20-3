package codegym.c10.assignmentspring.model.service.impl;

import codegym.c10.assignmentspring.model.entity.Transaction;
import codegym.c10.assignmentspring.model.repository.ITransactionRepository;
import codegym.c10.assignmentspring.model.service.ITransactionService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.Set;

@Validated
@Service
public class ITransactionServiceImpl implements ITransactionService {
    @Autowired
    private ITransactionRepository iTransactionRepository;

    private final Validator validator;

    public ITransactionServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        return iTransactionRepository.findAll(pageable);
    }

    @Override
    public Page<Transaction> findAllByServiceTypeContaining(Pageable pageable, String serviceType) {
        return iTransactionRepository.findAllByServiceTypeContaining(pageable, serviceType);
    }


    @Override
    public Iterable<Transaction> findAll() {
        return iTransactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction transaction) {
        validateTransaction(transaction);
        iTransactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return iTransactionRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iTransactionRepository.deleteById(id);
    }

    private void validateTransaction(Transaction transaction) {
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Dữ liệu không hợp lệ.");
            throw new ConstraintViolationException(errorMessage, violations);
        }
    }
}
