package com.agrodemanda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * BLOCO 2: BACK-END E MODELAGEM DE DADOS
 * Responsavel: Pedro Henrique
 * 
 * Esta classe representa a entidade "Demanda".
 * O Hibernate le as anotacoes abaixo e cria automaticamente 
 * a tabela fisica "tb_demandas" dentro do banco de dados H2.
 */
@Entity
@Table(name = "tb_demandas")
public class Demanda {

    // Identificador unico da linha no banco (Chave Primaria).
    // O IDENTITY faz o banco incrementar o ID sozinho: 1, 2, 3...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Codigo unico de rastreio para o agricultor (Ex: AGRO-1042).
    // nullable = false -> Obrigatorio preencher.
    // unique = true   -> O banco nao permite dois protocolos repetidos.
    @Column(nullable = false, unique = true)
    private String protocolo;

    // Nome completo do agricultor ou representante que abriu a solicitacao
    @Column(nullable = false)
    private String nomeSolicitante;

    // Telefone ou WhatsApp de contato para a Secretaria retornar
    @Column(nullable = false)
    private String telefone;

    // Comunidade rural de Santa Cruz do Capibaribe (Magana, Barra da Cruz, etc.)
    @Column(nullable = false)
    private String comunidade;

    // Tipo de servico solicitado (Preparo de solo, Estradas, Recursos hidricos...)
    @Column(nullable = false)
    private String tipoServico;

    // Detalhamento do pedido feito pelo agricultor (campo de texto com ate 1000 caracteres)
    @Column(length = 1000)
    private String descricao;

    // Ciclo de vida da solicitacao (PENDENTE, EM_ATENDIMENTO, CONCLUIDO).
    // EnumType.STRING grava a palavra em texto no banco em vez de um numero.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDemanda status;

    // Data e hora exatas em que o registro foi criado
    private LocalDateTime dataCriacao;

    /**
     * @PrePersist: Executado automaticamente pelo JPA um instante antes de salvar no banco.
     * Garante que toda demanda nova ja nasca com a data atual e status PENDENTE.
     */
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusDemanda.PENDENTE;
        }
    }

    public Demanda() {
    }

    public Demanda(String protocolo, String nomeSolicitante, String telefone, String comunidade, String tipoServico, String descricao) {
        this.protocolo = protocolo;
        this.nomeSolicitante = nomeSolicitante;
        this.telefone = telefone;
        this.comunidade = comunidade;
        this.tipoServico = tipoServico;
        this.descricao = descricao;
        this.status = StatusDemanda.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
    }

    // --- GETTERS E SETTERS (Permitem ler e alterar os dados com seguranca) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusDemanda getStatus() {
        return status;
    }

    public void setStatus(StatusDemanda status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // Metodos auxiliares de compatibilidade (para ligar os campos do HTML da Livia ao banco)
    public String getNome() {
        return nomeSolicitante;
    }

    public void setNome(String nome) {
        this.nomeSolicitante = nome;
    }

    public String getTipoNecessidade() {
        return tipoServico;
    }

    public void setTipoNecessidade(String tipoNecessidade) {
        this.tipoServico = tipoNecessidade;
    }
}
