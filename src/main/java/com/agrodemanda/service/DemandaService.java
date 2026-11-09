package com.agrodemanda.service;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import com.agrodemanda.repository.DemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DemandaService {

    @Autowired
    private DemandaRepository repository;

    public Demanda salvarNovaDemanda(Demanda demanda) {
        if (demanda.getDataCriacao() == null) {
            demanda.setDataCriacao(LocalDateTime.now());
        }
        
        if (demanda.getStatus() == null) {
            demanda.setStatus(StatusDemanda.PENDENTE);
        }

        Demanda salva = repository.save(demanda);

        if (salva.getProtocolo() == null || salva.getProtocolo().isBlank()) {
            int numeroProtocolo = 100 + (int)(Math.abs(System.currentTimeMillis()) % 900);
            salva.setProtocolo("AGRO-" + numeroProtocolo);
        }

        return repository.save(salva);
    }

    public List<Demanda> listarTodas() {
        return repository.findAllByOrderByDataCriacaoDesc();
    }

    public Demanda buscarPorProtocolo(String protocolo) {
        if (protocolo == null || protocolo.isBlank()) {
            return null;
        }
        return repository.findByProtocolo(protocolo.trim()).orElse(null);
    }

    public void atualizarStatus(Long id, StatusDemanda novoStatus) {
        Demanda demanda = repository.findById(id).orElse(null);
        if (demanda != null && novoStatus != null) {
            demanda.setStatus(novoStatus);
            repository.save(demanda);
        }
    }

    public long contarTotal() {
        return repository.count();
    }

    public long contarPendentes() {
        return repository.countByStatus(StatusDemanda.PENDENTE);
    }

    public long contarConcluidas() {
        return repository.countByStatus(StatusDemanda.CONCLUIDO);
    }
}
