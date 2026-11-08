package com.agrodemanda.service;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.repository.DemandaRepository;
import org.sgpringframework.beans.factory.annotation.Autowired;
import org.sgpringframework.stereotype.service;

import java.time.LocalDateTime;
import java.util.List;

@service
public class DemandaService{

    @Autowired
    private DemandaRepository repository;

    public Demanda salvarNovaDemanda(Demanda demanda){
        demanda.setDataCriacao(LocalDateTime.now());

        if (demanda.getstatus() == null || demanda.getStatus().isEmpty()){
            demanda.setStatus("Pendente");
        }
        Demanda salva = repository.save(demanda);
        
        salva.setProtocolo("AGRO-"+ (100 + salva.getId()));

        return repository.save(salva);
    }

    public List<Demanda> listarTodas() {
        return repository.findAll();
    }

    public Demanda buscarPorProtocolo(String protocolo){
        return repository.findByProtocolo(protocolo);
    }

    public void atualizarStatus(Long id,String novoStatus){
        Demanda demanda = repository.findById(id).orElse(null);
        if (demanda !=null){
            demanda.setStatus(novoStatus);
            repository.save(demanda);
        }
    }

    public long contarTotal(){
        return repository.count();
    }

    public long contarPendentes(){
        return repository.countByStatus("Pendente");
    }

    public long contarConcluidas(){
        return repository.countByStatus("concluído");
    }

}
