package br.com.passos.med_voll.controller;

import br.com.passos.med_voll.domain.agendamento.AgendaDeConsultas;
import br.com.passos.med_voll.domain.agendamento.DadosAgendamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.DadosDetalhamentoConsulta;
import br.com.passos.med_voll.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informações estão invalidas")
    @WithMockUser
    public void agendar_cenario1() throws Exception{
        // Arrange
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        // Act
        // Não é necessário fazer nada aqui, pois o erro deve ocorrer na requisição acima
        // Isso é apenas para simular o envio de uma requisição inválida sem dados

        // Assert
        // Verifique se a consulta foi agendada corretamente
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão validas")
    @WithMockUser
    public void agendar_cenario2() throws Exception{
        // Arrange
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        // Act
        var response = mvc
                .perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJson.write(
                        new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
                ).getJson())
                )
                .andReturn().getResponse();


        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                dadosDetalhamento
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}