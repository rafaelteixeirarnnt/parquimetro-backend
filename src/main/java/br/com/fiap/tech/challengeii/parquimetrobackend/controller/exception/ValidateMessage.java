package br.com.fiap.tech.challengeii.parquimetrobackend.controller.exception;

import lombok.Getter;

@Getter
public class ValidateMessage {

    private String campo;
    private String mensagem;

    public ValidateMessage() {
    }

    public ValidateMessage(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
