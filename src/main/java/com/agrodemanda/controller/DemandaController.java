package com.agrodemanda.controller;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import com.agrodemanda.service.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
public class DemandaController {

    @Autowired
    private DemandaService service;

    @GetMapping("/")
    public String portalAgricultor(@RequestParam(value = "protocoloConsulta", required = false) String protocoloConsulta, Model model) {
        model.addAttribute("demanda", new Demanda());

        if (protocoloConsulta != null && !protocoloConsulta.isBlank()) {
            Demanda resultado = service.buscarPorProtocolo(protocoloConsulta.trim());
            if (resultado != null) {
                model.addAttribute("resultadoConsulta", List.of(resultado));
            } else {
                model.addAttribute("resultadoConsulta", Collections.emptyList());
                model.addAttribute("mensagemErro", "Nenhuma solicitação encontrada para o protocolo: " + protocoloConsulta);
            }
        }
        return "index";
    }

    @PostMapping({"/demandas", "/demanda/salvar"})
    public String salvarDemanda(@ModelAttribute("demanda") Demanda demanda, RedirectAttributes redirectAttrs) {
        Demanda salva = service.salvarNovaDemanda(demanda);
        redirectAttrs.addFlashAttribute(
                "mensagemSucesso",
                "Solicitação enviada com sucesso! Seu protocolo é: " + salva.getProtocolo()
        );
        return "redirect:/";
    }

    @GetMapping("/demanda/consultar")
    public String consultarProtocolo(@RequestParam(value = "protocolo", required = false) String protocolo, Model model) {
        return portalAgricultor(protocolo, model);
    }

    @GetMapping({"/painel", "/secretaria"})
    public String painelSecretaria(Model model) {
        model.addAttribute("demandas", service.listarTodas());
        
        long total = service.contarTotal();
        long pendentes = service.contarPendentes();
        long concluidas = service.contarConcluidas();

        // Atributos para compatibilidade com qualquer versão do template
        model.addAttribute("total", total);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("concluidas", concluidas);
        model.addAttribute("totalDemandas", total);
        model.addAttribute("pendentesDemandas", pendentes);
        model.addAttribute("concluidasDemandas", concluidas);
        
        return "painel";
    }

    @PostMapping("/demandas/{id}/concluir")
    public String concluirDemanda(@PathVariable("id") Long id) {
        service.atualizarStatus(id, StatusDemanda.CONCLUIDO);
        return "redirect:/painel";
    }

    @PostMapping("/secretaria/atualizar-status")
    public String atualizarStatus(@RequestParam("id") Long id, @RequestParam("status") String novoStatus) {
        service.atualizarStatus(id, converterStatus(novoStatus));
        return "redirect:/painel";
    }

    private StatusDemanda converterStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }

        String valor = status.trim();
        try {
            return StatusDemanda.valueOf(valor);
        } catch (IllegalArgumentException ignored) {
            String normalizado = valor
                    .toUpperCase()
                    .replace("Ç", "C")
                    .replace("Ã", "A")
                    .replace("Á", "A")
                    .replace("É", "E")
                    .replace("Í", "I")
                    .replace("Ó", "O")
                    .replace("Ô", "O")
                    .replace("Ú", "U");

            switch (normalizado) {
                case "PENDENTE":
                    return StatusDemanda.PENDENTE;
                case "EM ATENDIMENTO":
                    return StatusDemanda.EM_ATENDIMENTO;
                case "CONCLUIDO":
                case "CONCLUIDA":
                    return StatusDemanda.CONCLUIDO;
                default:
                    throw new IllegalArgumentException("Status inválido: " + status);
            }
        }
    }

    private StatusDemanda converterStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }

        String valor = status.trim();

        try {
            return StatusDemanda.valueOf(valor);
        } catch (IllegalArgumentException ignored) {
            String normalizado = valor
                    .toUpperCase()
                    .replace("-", "_")
                    .replace(" ", "_")
                    .replace("Á", "A")
                    .replace("À", "A")
                    .replace("Ã", "A")
                    .replace("Â", "A")
                    .replace("É", "E")
                    .replace("Í", "I")
                    .replace("Ó", "O")
                    .replace("Õ", "O")
                    .replace("Ú", "U")
                    .replace("Ç", "C");

            try {
                return StatusDemanda.valueOf(normalizado);
            } catch (IllegalArgumentException e) {
                String semAcento = normalizado
                        .replace("_", " ")
                        .toLowerCase();

                switch (semAcento) {
                    case "pendente":
                        return StatusDemanda.PENDENTE;
                    case "em atendimento":
                        return StatusDemanda.EM_ATENDIMENTO;
                    case "concluido":
                        return StatusDemanda.CONCLUIDO;
                    default:
                        throw new IllegalArgumentException("Status inválido: " + status);
                }
            }
        }
    }
}
