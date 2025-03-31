package by.diploma.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 9, max = 13)
    @Pattern(regexp = "^(\\+375)?(44|29|25|33)[0-9]{7}$")
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Past
    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Size(min = 8, max = 255)
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<UserSubscriptions> userSubscriptions;

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user")
    private List<VisitHistory> visitHistories;
}
