package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.nutrihealthplan.dietapp.model.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAccessEntity> accessEntities =  new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private List<MealProductEntity> productSelectionEntities = new ArrayList<>();

}
