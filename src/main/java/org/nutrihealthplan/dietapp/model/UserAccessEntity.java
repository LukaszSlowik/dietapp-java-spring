package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.*;
import org.nutrihealthplan.dietapp.model.enums.AccessPermission;

import java.util.HashSet;
import java.util.Set;
@Entity
public class UserAccessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private UserEntity target;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_access_permissions",
            joinColumns = @JoinColumn(name= "access_id"))
    @Enumerated(EnumType.STRING)
    @Column(name= "permission")
    private Set<AccessPermission> permissions = new HashSet<>();
}
