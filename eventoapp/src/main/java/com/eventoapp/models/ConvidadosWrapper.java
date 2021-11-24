package com.eventoapp.models;

public class ConvidadosWrapper {
    private Iterable<Convidado> convidados;
    
    public ConvidadosWrapper() {}

    public ConvidadosWrapper(Iterable<Convidado> convidados) {
        this.convidados = convidados;
    }


    public Iterable<Convidado> getConvidados() {
        return convidados;
    }

    public void setConvidados(Iterable<Convidado> convidados) {
        this.convidados = convidados;
    }
}