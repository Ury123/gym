package by.diploma.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "amount_of_trainings", nullable = false)
    private Integer amountOfTrainings;

    @NotNull
    @Column(name = "validity_period", nullable = false)
    private Period validityPeriod;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "subscription")
    private List<UserSubscription> userSubscriptions;
}