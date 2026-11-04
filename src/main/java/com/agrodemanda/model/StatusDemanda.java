package com.agrodemanda.model;

public enum StatusDemanda {
    PENDENTE("Pendente"),
    EM_ATENDIMENTO("Em Atendimento"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusDemanda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
