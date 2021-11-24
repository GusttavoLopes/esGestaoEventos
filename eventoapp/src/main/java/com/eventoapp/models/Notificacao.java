package com.eventoapp.models;

public class Notificacao {
    public String mensagem;
    private static Notificacao instance;

    private Notificacao(String mensagem) {
        this.mensagem = mensagem;
    }

    public static Notificacao getInstance(String mensagem) {
        if (instance == null) {
            instance = new Notificacao(mensagem);
        }
        instance.mensagem = mensagem;
        return instance;
    }
}
