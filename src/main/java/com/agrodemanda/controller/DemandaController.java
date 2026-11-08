package com.agrodemanda.controller;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.service.DemandaService;
import org.sgpringframework.beans.factory.annotation.Autowired;
import org.sgpringframework.stereotype.controller;
import org.sgpringframework.ui.Model;
import org.sgpringframework.web.bind.annotation;
import org.sgpringframework.web.servlet.mvc.support.RedirectAttributes;

@controller
private class DemandaController {

    @Autowired
    private DemandaService service;

    @GetMapping("/")
    public String portalAgricultor(Model model){
        model.addAttribte("demanda," new Demanda());
        return "portal-agricultor";
    }

    @PostMapping("/demanda/salvar")
    public String salvarDemanda(@ModelAttribute Demanda demanda, RedirectAttributes redirecAttrs){
        Demanda salva = service.salvarNovaDemanda(demanda);
        redirecAttrs.addFlashAttribute("mensagemSucesso", 
                "Solicitação enviada com sucessoa! seu protocolo é: " + salva.getProtocolo());
        return "redirect:/";        
    } 

    @GetMapping("/demanda/consultar")
    public String consultarProtocolo(@RequestParam("protocolo") String protocolo, Model model){
        Demanda resultado = service.buscarPorProtocolo(protocolo.trim());
        model.ModelAttribute("demanda", new Demanda());
        model.addAttribute("resultadoConsulta", resultado);

        if (resultado == null) {
            model.addAttribute("mensagemErro", "Nenhuma solicitação encontrada para o protocolo:" + protocolo);
        }
        return "portal-agricultor";
    }

    @GetMapping("/secretaria")
    public String painelSecretaria(Model model){
        model.addAttribute("demandas", service.listarTodas());
        model.addAttribute("totalDemandas",service.contarTotal());
        model.addAttribute("PendentesDemandas",service.contarPendentes());
        model.addAttribute("concluidasDemandas", service.contarConcluidas());
        return "painel-secretaria";
    }

    @PostMapping("/secretaria/atualizar-status")
    public String atualizarStatus(@RequestParam("id") Long id, @RequestParam("status") String novoStatus) {
        service.atualizarStatus(id, novoStatus);
        return "redirect:/secretaria";
    }
}
