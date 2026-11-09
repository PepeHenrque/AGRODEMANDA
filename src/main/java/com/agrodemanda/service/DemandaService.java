package com.agrodemanda.service;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import com.agrodemanda.repository.DemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CAMADA DE SERVICO (SERVICE)
 * 
 * Papel: Concentra as regras de negocio e a logica do sistema.
 * Ele fica no meio do caminho entre o Controller (que atende o usuario)
 * e o Repository (que conversa com o banco de dados).
 */
@Service
public class DemandaService {

    // Injeta o repositorio para salvar e buscar informacoes no banco H2
    @Autowired
    private DemandaRepository repository;

    /**
     * Regra de criacao de uma nova demanda:
     * 1. Garante que o status comece como PENDENTE.
     * 2. Gera automaticamente um codigo de protocolo unico (ex: AGRO-1042).
     * 3. Salva no banco de dados atraves do repositorio.
     */
    public Demanda salvarNovaDemanda(Demanda demanda) {
        if (demanda.getDataCriacao() == null) {
            demanda.setDataCriacao(LocalDateTime.now());
        }
        
        if (demanda.getStatus() == null) {
            demanda.setStatus(StatusDemanda.PENDENTE);
        }

        // Garante que o protocolo seja gerado antes de gravar no banco
        if (demanda.getProtocolo() == null || demanda.getProtocolo().isBlank()) {
            int numeroProtocolo = 1000 + (int)(Math.abs(System.currentTimeMillis()) % 9000);
            demanda.setProtocolo("AGRO-" + numeroProtocolo);
        }

        return repository.save(demanda);
    }

    /**
     * Retorna todas as demandas cadastradas, ordenadas da mais recente para a mais antiga.
     */
    public List<Demanda> listarTodas() {
        return repository.findAllByOrderByDataCriacaoDesc();
    }

    /**
     * Busca uma demanda especifica pelo protocolo digitado pelo agricultor no celular.
     * Trata o formato com ou sem traco para evitar erros de digitacao do usuario.
     */
    public Demanda buscarPorProtocolo(String protocolo) {
        if (protocolo == null || protocolo.isBlank()) {
            return null;
        }
        String limpo = protocolo.trim();
        Demanda resultado = repository.findByProtocolo(limpo).orElse(null);
        if (resultado != null) {
            return resultado;
        }

        // Caso o agricultor digite sem traco (ex: AGRO1042), tenta buscar com traco (AGRO-1042)
        if (!limpo.contains("-") && limpo.length() > 4 && limpo.toUpperCase().startsWith("AGRO")) {
            String comTraco = "AGRO-" + limpo.substring(4);
            resultado = repository.findByProtocolo(comTraco).orElse(null);
            if (resultado != null) {
                return resultado;
            }
        }

        return null;
    }

    /**
     * Atualiza o status da demanda no banco (ex: de Pendente para Concluido).
     */
    public void atualizarStatus(Long id, StatusDemanda novoStatus) {
        Demanda demanda = repository.findById(id).orElse(null);
        if (demanda != null && novoStatus != null) {
            demanda.setStatus(novoStatus);
            repository.save(demanda);
        }
    }

    /**
     * Contagens para os 3 cartoes de resumo do painel da secretaria:
     */
    public long contarTotal() {
        return repository.count();
    }

    public long contarPendentes() {
        return repository.countByStatus(StatusDemanda.PENDENTE);
    }

    public long contarConcluidas() {
        return repository.countByStatus(StatusDemanda.CONCLUIDO) + repository.countByStatus(StatusDemanda.CONCLUIDA);
    }
}
