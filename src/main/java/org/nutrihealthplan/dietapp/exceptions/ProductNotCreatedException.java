package org.nutrihealthplan.dietapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ProductNotCreatedException extends RecordNotCreatedException{
    public ProductNotCreatedException(){
        super("Failed to create the product");
    }
    public ProductNotCreatedException(String message) {
        super(message);
    }
}
