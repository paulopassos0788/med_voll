package br.com.passos.med_voll.domain.agendamento.validacoes.agendamento;

import br.com.passos.med_voll.domain.agendamento.DadosAgendamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.ValidacaoException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(dataConsulta, agora).toMinutes();

        if(diferencaEmMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
