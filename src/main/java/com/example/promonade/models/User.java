package com.example.promonade.models;

import com.example.promonade.enums.customerEnums.Gender;
import com.example.promonade.enums.userEnums.ERole;
import com.example.promonade.enums.userEnums.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotBlank
    @Size(max = 120)
    private String username;

    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password1;

    @Column(unique = true,nullable = false)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    @Enumerated(EnumType.STRING)
    ERole role;

    @Enumerated(EnumType.STRING)
    Team team;

    @CreationTimestamp
    Date createAt;

}
