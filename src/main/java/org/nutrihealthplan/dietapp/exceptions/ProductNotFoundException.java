package org.nutrihealthplan.dietapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ProductNotFoundException extends RecordNotFoundException{
    public ProductNotFoundException(Long productId){

        super("Product with id " + productId + " not found");
    }
}
