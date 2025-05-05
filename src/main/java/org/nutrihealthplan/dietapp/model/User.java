package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.nutrihealthplan.dietapp.model.enums.AuthProvider;
import org.nutrihealthplan.dietapp.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAccessEntity> accessEntities =  new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private List<MealProductEntity> productSelectionEntities = new ArrayList<>();
    private String username;

}
