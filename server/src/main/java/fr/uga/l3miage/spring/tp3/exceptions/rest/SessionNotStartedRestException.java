package fr.uga.l3miage.spring.tp3.exceptions.rest;

public class SessionNotStartedRestException extends Exception {
    public SessionNotStartedRestException(String message) {
        super(message);
    }
}
