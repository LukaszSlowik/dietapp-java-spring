package org.nutrihealthplan.dietapp.exceptions;

public class AuthenticatedUserNotFoundException extends RuntimeException{
    public AuthenticatedUserNotFoundException(){
        super("No authenticated user found");
    }
}
