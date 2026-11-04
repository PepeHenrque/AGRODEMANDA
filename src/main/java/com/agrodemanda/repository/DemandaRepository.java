package com.agrodemanda.repository;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {

    // Busca exata por protocolo (para a consulta do agricultor)
    Optional<Demanda> findByProtocolo(String protocolo);

    // Contagem de demandas por status para os cards do painel
    long countByStatus(StatusDemanda status);

    // Listagem por ordem decrescente de data
    List<Demanda> findAllByOrderByDataCriacaoDesc();

    // Listagem filtrada por comunidade
    List<Demanda> findByComunidadeIgnoreCase(String comunidade);

    // Listagem filtrada por status
    List<Demanda> findByStatus(StatusDemanda status);
}
