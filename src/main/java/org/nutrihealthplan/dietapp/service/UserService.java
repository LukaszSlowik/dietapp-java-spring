package org.nutrihealthplan.dietapp.service;

import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void deleteUser(Long userId) {
    List<ProductEntity> privateProducts = productRepository.findByOwnerIdAndScope(userId, Scope.PRIVATE);
        productRepository.deleteAll(privateProducts);


    List<ProductEntity> globalProducts = productRepository.findByOwnerIdAndScope(userId, Scope.GLOBAL);
        globalProducts.forEach(product -> product.setOwner(null));
        productRepository.saveAll(globalProducts);


        userRepository.deleteById(userId);
}}
