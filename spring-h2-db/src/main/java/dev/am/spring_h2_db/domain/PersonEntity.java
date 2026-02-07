package dev.am.spring_h2_db.domain;

import dev.am.spring_h2_db.domain.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class PersonEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String names;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    private String email;

    public PersonEntity(String names, String lastName, MaritalStatus maritalStatus, LocalDate birthDate, String email) {
        this.names = names;
        this.lastName = lastName;
        this.maritalStatus = maritalStatus;
        this.birthDate = birthDate;
        this.email = email;
    }

    public PersonEntity(Integer id, String names, String lastName, MaritalStatus maritalStatus, LocalDate birthDate, String email) {
        this.id = id;
        this.names = names;
        this.lastName = lastName;
        this.maritalStatus = maritalStatus;
        this.birthDate = birthDate;
        this.email = email;
    }
}
