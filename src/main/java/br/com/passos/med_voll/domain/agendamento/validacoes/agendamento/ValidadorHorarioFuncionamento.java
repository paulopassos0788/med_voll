package br.com.passos.med_voll.domain.agendamento.validacoes.agendamento;

import br.com.passos.med_voll.domain.agendamento.DadosAgendamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.ValidacaoException;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = dataConsulta.getHour() < 7;
        var depoisDoFechamento = dataConsulta.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoFechamento){
            throw new ValidacaoException("Consulta fora do horário de funcionamento");
        }
    }

}
