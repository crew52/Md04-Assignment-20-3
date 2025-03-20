package codegym.c10.assignmentspring.model.repository;

import codegym.c10.assignmentspring.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAll(Pageable pageable);
    Page<Transaction> findAllByServiceTypeContaining(Pageable pageable, String serviceType);
}


