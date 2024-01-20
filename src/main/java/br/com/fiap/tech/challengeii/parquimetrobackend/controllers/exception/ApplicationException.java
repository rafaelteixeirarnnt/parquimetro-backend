package br.com.fiap.tech.challengeii.parquimetrobackend.controllers.exception;

public class ApplicationException extends RuntimeException {

    //TODO: Melhorar depois essa exception
    public ApplicationException(String message) {
        super(message);
    }
}