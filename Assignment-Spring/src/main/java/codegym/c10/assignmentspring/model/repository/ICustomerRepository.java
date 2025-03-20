package codegym.c10.assignmentspring.model.repository;

import codegym.c10.assignmentspring.model.entity.Customer;
import codegym.c10.assignmentspring.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
