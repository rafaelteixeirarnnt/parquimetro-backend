package br.com.fiap.tech.challengeii.parquimetrobackend.controller.exception;

public class ControllerNotFoundException extends RuntimeException {
    public ControllerNotFoundException(String message) {
        super(message);
    }
}
