package br.com.passos.med_voll.domain.agendamento.validacoes.cancelamento;

import br.com.passos.med_voll.domain.agendamento.ConsultaRepository;
import br.com.passos.med_voll.domain.agendamento.DadosCancelamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var dataAgora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(dataAgora, consulta.getData()).toHours();

        if(diferencaEmHoras < 24){
            throw new ValidacaoException("Consulta só pode ser cancelada com 24 horas de antecedência");
        }

    }
}
