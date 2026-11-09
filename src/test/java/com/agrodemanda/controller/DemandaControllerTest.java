package com.agrodemanda.controller;

import com.agrodemanda.model.Demanda;
import com.agrodemanda.model.StatusDemanda;
import com.agrodemanda.service.DemandaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DemandaController.class)
class DemandaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DemandaService service;

    @BeforeEach
    void setup() {
        Demanda demanda = new Demanda();
        demanda.setId(1L);
        demanda.setProtocolo("AGRO-2026-0001");
        demanda.setNomeSolicitante("Maria da Silva");
        demanda.setTelefone("(81) 99999-0000");
        demanda.setComunidade("Magana");
        demanda.setTipoServico("Preparo de Solo");
        demanda.setStatus(StatusDemanda.EM_ATENDIMENTO);

        when(service.listarTodas()).thenReturn(List.of(demanda));
        when(service.contarTotal()).thenReturn(1L);
        when(service.contarPendentes()).thenReturn(0L);
        when(service.contarConcluidas()).thenReturn(0L);
    }

    @Test
    void painelDeveExibirFormularioDeAtualizacaoParaStatusEmAtendimento() throws Exception {
        mockMvc.perform(get("/painel"))
                .andExpect(status().isOk())
                .andExpect(view().name("painel"))
                .andExpect(content().string(containsString("Em Atendimento")))
                .andExpect(content().string(containsString("Atualizar")));
    }

    @Test
    void atualizarStatusDeveAceitarEnumEmFormatoNome() throws Exception {
        mockMvc.perform(post("/secretaria/atualizar-status")
                        .param("id", "1")
                        .param("status", "EM_ATENDIMENTO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/secretaria"));

        verify(service).atualizarStatus(1L, StatusDemanda.EM_ATENDIMENTO);
    }
}
