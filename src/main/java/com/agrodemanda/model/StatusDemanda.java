package com.agrodemanda.model;

/**
 * BLOCO 2: BACK-END E MODELAGEM DE DADOS
 * Responsavel: Pedro Henrique
 * 
 * Este Enum define as unicas opcoes aceitas para o ciclo de vida de uma solicitacao.
 * Evita que o usuario ou o sistema salve textos errados no banco de dados.
 */
public enum StatusDemanda {
    PENDENTE("Pendente"),          // O agricultor acabou de enviar; aguardando analise
    EM_ATENDIMENTO("Em Atendimento"), // A secretaria esta executando o servico no campo
    CONCLUIDO("Concluído"),        // Servico finalizado com sucesso
    CONCLUIDA("Concluído");        // Alias de compatibilidade com os templates

    private final String descricao;

    StatusDemanda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
