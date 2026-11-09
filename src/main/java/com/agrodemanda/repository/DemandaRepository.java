package com.agrodemanda.repository;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * BLOCO 2: BACK-END E CAMADA DE ACESSO A DADOS
 * Responsavel: Pedro Henrique
 * 
 * Esta interface utiliza o Spring Data JPA.
 * Ao herdar JpaRepository<Demanda, Long>, o Spring nos fornece automaticamente:
 * save(), findById(), findAll(), delete() sem precisarmos escrever SQL manual.
 */
@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {

    /**
     * Query Method inteligente:
     * O Spring interpreta o nome "findByProtocolo" e gera em tempo de execucao:
     * SELECT * FROM tb_demandas WHERE protocolo = ?
     * Usado para o agricultor rastrear o pedido digitando o protocolo no celular.
     */
    Optional<Demanda> findByProtocolo(String protocolo);

    /**
     * Query Method de contagem:
     * Gera: SELECT COUNT(*) FROM tb_demandas WHERE status = ?
     * Usado para alimentar os cards informativos de "Pendentes" e "Concluidas" no painel.
     */
    long countByStatus(StatusDemanda status);

    /**
     * Busca todas as demandas ordenando da mais recente para a mais antiga.
     * Gera: SELECT * FROM tb_demandas ORDER BY data_criacao DESC
     */
    List<Demanda> findAllByOrderByDataCriacaoDesc();

    /**
     * Permite filtrar as demandas por comunidade rural (ex: todas da "Magana").
     */
    List<Demanda> findByComunidadeIgnoreCase(String comunidade);

    /**
     * Permite filtrar as demandas diretamente por status.
     */
    List<Demanda> findByStatus(StatusDemanda status);
}
