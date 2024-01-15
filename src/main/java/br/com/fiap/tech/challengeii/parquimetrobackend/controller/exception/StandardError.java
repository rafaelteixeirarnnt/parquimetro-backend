package br.com.fiap.tech.challengeii.parquimetrobackend.controller.exception;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {}

}