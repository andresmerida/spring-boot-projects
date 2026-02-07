package dev.am.spring_h2_db.web.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public static PersonNotFoundException of(Integer code) {
        return new PersonNotFoundException("Person with code " + code + " not found");
    }
}
