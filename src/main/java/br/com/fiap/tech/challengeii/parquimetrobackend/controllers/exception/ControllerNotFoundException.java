package br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String message) {
        super(message);
    }
}