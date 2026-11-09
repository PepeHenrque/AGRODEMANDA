package com.agrodemanda.controller;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import com.agrodemanda.service.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DemandaController {

    @Autowired
    private DemandaService service;

    @GetMapping("/")
    public String portalAgricultor(Model model) {
        model.addAttribute("demanda", new Demanda());
        return "index";
    }

    @PostMapping({"/demandas", "/demanda/salvar"})
    public String salvarDemanda(@ModelAttribute("demanda") Demanda demanda, RedirectAttributes redirectAttributes) {
        Demanda salva = service.salvarNovaDemanda(demanda);
        redirectAttributes.addFlashAttribute(
                "mensagemSucesso",
                "Solicitação enviada com sucesso! Seu protocolo é: " + salva.getProtocolo()
        );
        return "redirect:/";
    }

    @GetMapping("/demanda/consultar")
    public String consultarProtocolo(@RequestParam(value = "protocolo", required = false) String protocolo, Model model) {
        model.addAttribute("demanda", new Demanda());

        if (protocolo == null || protocolo.isBlank()) {
            model.addAttribute("mensagemErro", "Informe um protocolo para consultar.");
            model.addAttribute("resultadoConsulta", null);
            return "index";
        }

        Demanda resultado = service.buscarPorProtocolo(protocolo.trim());
        model.addAttribute("resultadoConsulta", resultado);

        if (resultado == null) {
            model.addAttribute("mensagemErro", "Nenhuma solicitação encontrada para o protocolo: " + protocolo);
        }

        return "index";
    }

    @GetMapping({"/painel", "/secretaria"})
    public String painelSecretaria(Model model) {
        model.addAttribute("demandas", service.listarTodas());
        model.addAttribute("totalDemandas", service.contarTotal());
        model.addAttribute("pendentesDemandas", service.contarPendentes());
        model.addAttribute("concluidasDemandas", service.contarConcluidas());
        model.addAttribute("total", service.contarTotal());
        model.addAttribute("pendentes", service.contarPendentes());
        model.addAttribute("concluidas", service.contarConcluidas());
        return "painel";
    }

    @PostMapping("/secretaria/atualizar-status")
    public String atualizarStatus(@RequestParam("id") Long id, @RequestParam("status") String novoStatus) {
        service.atualizarStatus(id, converterStatus(novoStatus));
        return "redirect:/secretaria";
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
