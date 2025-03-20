package codegym.c10.assignmentspring.model.service;

import codegym.c10.assignmentspring.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransactionService extends IGenerateService<Transaction>{
    Page<Transaction> findAll(Pageable pageable);
    Page<Transaction> findAllByServiceTypeContaining(Pageable pageable, String serviceType);
}
