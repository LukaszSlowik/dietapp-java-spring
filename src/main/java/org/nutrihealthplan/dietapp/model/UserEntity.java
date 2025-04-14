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
    @OneToMany(mappedBy = "owner")
    private List<MealProductEntity> productSelectionEntities = new ArrayList<>();


//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public List<ProductSelectionEntity> getProductSelectionEntities() {
//        return productSelectionEntities;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public void setProductSelectionEntities(List<ProductSelectionEntity> productSelectionEntities) {
//        this.productSelectionEntities = productSelectionEntities;
//    }
}
