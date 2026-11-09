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

        // Garante que o protocolo seja gerado ANTES de salvar (coluna não pode ser nula)
        if (demanda.getProtocolo() == null || demanda.getProtocolo().isBlank()) {
            int numeroProtocolo = 1000 + (int)(Math.abs(System.currentTimeMillis()) % 9000);
            demanda.setProtocolo("AGRO-" + numeroProtocolo);
        }

        return repository.save(demanda);
    }

    public List<Demanda> listarTodas() {
        return repository.findAllByOrderByDataCriacaoDesc();
    }

    public Demanda buscarPorProtocolo(String protocolo) {
        if (protocolo == null || protocolo.isBlank()) {
            return null;
        }
        String limpo = protocolo.trim();
        Demanda resultado = repository.findByProtocolo(limpo).orElse(null);
        if (resultado != null) {
            return resultado;
        }

        // Caso o usuário consulte sem o traço (ex.: AGRO1234)
        if (!limpo.contains("-") && limpo.length() > 4 && limpo.toUpperCase().startsWith("AGRO")) {
            String comTraco = "AGRO-" + limpo.substring(4);
            resultado = repository.findByProtocolo(comTraco).orElse(null);
            if (resultado != null) {
                return resultado;
            }
        }

        return null;
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
        return repository.countByStatus(StatusDemanda.CONCLUIDO) + repository.countByStatus(StatusDemanda.CONCLUIDA);
    }
}
