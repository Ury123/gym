package by.diploma.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Column(name = "amount_of_trainings", nullable = false)
    private Integer amountOfTrainings;

    @Column(name = "validity_period", nullable = false)
    private Integer validityPeriod;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "subscription")
    private List<UserSubscriptions> userSubscriptions;
}
