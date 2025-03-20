package codegym.c10.assignmentspring.controller;

import codegym.c10.assignmentspring.model.entity.Transaction;
import codegym.c10.assignmentspring.model.service.ICustomerService;
import codegym.c10.assignmentspring.model.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<Transaction>> findAllTransaction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> computers = transactionService.findAll(pageable);

        return computers.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(computers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findTransactionById(@PathVariable Long id) {
        Optional<Transaction> transactionOptional = transactionService.findById(id);
        return transactionOptional.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction transaction1 = transactionService.save(transaction);
        return transaction != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(transaction1)
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Transaction>> searchComputers(
            @RequestParam("search") Optional<String> search,
            Pageable pageable) {

        Page<Transaction> computers = search
                .map(s -> transactionService.findAllByServiceTypeContaining(pageable, s))
                .orElseGet(() -> transactionService.findAll(pageable));

        return computers.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(computers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteCustomer(@PathVariable Long id) {
        Optional<Transaction> transactionOptional = transactionService.findById(id);
        if (!transactionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transactionService.remove(id);
        return new ResponseEntity<>(transactionOptional.get(), HttpStatus.OK);
    }

}
