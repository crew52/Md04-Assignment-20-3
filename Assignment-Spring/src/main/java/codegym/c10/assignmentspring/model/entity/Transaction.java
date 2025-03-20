package codegym.c10.assignmentspring.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Transaction ID is required")
    @Pattern(regexp = "MGD-\\d{4}", message = "Transaction ID must follow the format MGD-XXXX (X = 0-9)")
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "Transaction date is required")
    @Future(message = "Transaction date must be in the future")
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @NotBlank(message = "Service type is required")
    @Pattern(regexp = "Land|House and Land", message = "Service type must be either 'Land' or 'House and Land'")
    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @NotNull(message = "Unit price is required")
    @Min(value = 500000, message = "Unit price must be greater than 500,000 VND")
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @NotNull(message = "Area is required")
    @Min(value = 20, message = "Area must be greater than 20 m²")
    @Column(name = "area", nullable = false)
    private Double area;
}

