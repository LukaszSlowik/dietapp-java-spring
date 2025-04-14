package org.nutrihealthplan.dietapp.exceptions;

public abstract class RecordNotCreatedException  extends RuntimeException{
    public RecordNotCreatedException(String message){
        super(message);
    }
}
