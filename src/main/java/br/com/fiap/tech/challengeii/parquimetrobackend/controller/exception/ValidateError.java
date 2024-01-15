package br.com.fiap.tech.challengeii.parquimetrobackend.controller.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidateError extends StandardError{

    private List<ValidateMessage> mensagens = new ArrayList<ValidateMessage>();

    public void addMessage(String campo, String mensagem){
        mensagens.add(new ValidateMessage(campo, mensagem));

    }
}
