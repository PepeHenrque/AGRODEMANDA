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

/**
 * CAMADA CONTROLLER (CONTROLADOR WEB)
 * 
 * Papel: E a porta de entrada da aplicacao.
 * Ele escuta as requisicoes HTTP do navegador (rotas como / e /painel),
 * pede as informacoes necessarias para o Service e devolve a pagina HTML correspondente.
 */
@Controller
public class DemandaController {

    // Injeta o Service para executar as regras de negocio e chamar o banco
    @Autowired
    private DemandaService service;

    /**
     * Rota da pagina inicial: GET http://localhost:8081/
     * Mostra o Portal do Agricultor (formulario de envio e consulta de protocolo).
     */
    @GetMapping("/")
    public String portalAgricultor(@RequestParam(value = "protocoloConsulta", required = false) String protocoloConsulta, Model model) {
        // Envia um objeto vazio para o formulario preencher
        model.addAttribute("demanda", new Demanda());

        // Se o agricultor digitou um protocolo no campo de busca, pesquisa no banco
        if (protocoloConsulta != null && !protocoloConsulta.isBlank()) {
            Demanda resultado = service.buscarPorProtocolo(protocoloConsulta.trim());
            if (resultado != null) {
                model.addAttribute("resultadoConsulta", List.of(resultado));
            } else {
                model.addAttribute("resultadoConsulta", Collections.emptyList());
                model.addAttribute("mensagemErro", "Nenhuma solicitação encontrada para o protocolo: " + protocoloConsulta);
            }
        }
        return "index"; // Retorna o template index.html
    }

    /**
     * Rota de salvamento: POST /demandas
     * Chamada quando o agricultor clica no botao "Enviar demanda".
     */
    @PostMapping({"/demandas", "/demanda/salvar"})
    public String salvarDemanda(@ModelAttribute("demanda") Demanda demanda, RedirectAttributes redirectAttrs) {
        // Salva a nova demanda e recebe o objeto com o protocolo gerado
        Demanda salva = service.salvarNovaDemanda(demanda);
        
        // Exibe a mensagem de sucesso com o numero de protocolo no topo da tela
        redirectAttrs.addFlashAttribute(
                "mensagemSucesso",
                "Solicitação enviada com sucesso! Seu protocolo é: " + salva.getProtocolo()
        );
        return "redirect:/"; // Recarrega a pagina inicial
    }

    /**
     * Rota alternativa de consulta por protocolo: GET /demanda/consultar
     */
    @GetMapping("/demanda/consultar")
    public String consultarProtocolo(@RequestParam(value = "protocolo", required = false) String protocolo, Model model) {
        return portalAgricultor(protocolo, model);
    }

    /**
     * Rota do Painel da Secretaria: GET /painel ou /secretaria
     * Carrega a lista completa de chamados e os contadores de demandas.
     */
    @GetMapping({"/painel", "/secretaria"})
    public String painelSecretaria(Model model) {
        // Busca todas as demandas cadastradas para exibir na tabela
        model.addAttribute("demandas", service.listarTodas());
        
        // Busca os totais para preencher os 3 cartoes do topo
        long total = service.contarTotal();
        long pendentes = service.contarPendentes();
        long concluidas = service.contarConcluidas();

        model.addAttribute("total", total);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("concluidas", concluidas);
        model.addAttribute("totalDemandas", total);
        model.addAttribute("pendentesDemandas", pendentes);
        model.addAttribute("concluidasDemandas", concluidas);
        
        return "painel"; // Retorna o template painel.html
    }

    /**
     * Rota de conclusao rapida: POST /demandas/{id}/concluir
     * Marca uma demanda como CONCLUIDO direto pelo botao do painel.
     */
    @PostMapping("/demandas/{id}/concluir")
    public String concluirDemanda(@PathVariable("id") Long id) {
        service.atualizarStatus(id, StatusDemanda.CONCLUIDO);
        return "redirect:/secretaria";
    }

    /**
     * Rota de atualizacao de status: POST /secretaria/atualizar-status
     * Atualiza o status selecionado (Pendente, Em Atendimento, Concluido) no painel.
     */
    @PostMapping("/secretaria/atualizar-status")
    public String atualizarStatus(@RequestParam("id") Long id, @RequestParam("status") String novoStatus) {
        service.atualizarStatus(id, converterStatus(novoStatus));
        return "redirect:/secretaria";
    }

    /**
     * Metodo auxiliar para converter texto vindo do HTML no Enum StatusDemanda
     */
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
                    case "concluida":
                        return StatusDemanda.CONCLUIDO;
                    default:
                        throw new IllegalArgumentException("Status inválido: " + status);
                }
            }
        }
    }
}
