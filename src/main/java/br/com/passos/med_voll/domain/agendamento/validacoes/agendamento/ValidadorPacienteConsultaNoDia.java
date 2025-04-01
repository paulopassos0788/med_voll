package br.com.passos.med_voll.domain.agendamento.validacoes.agendamento;

import br.com.passos.med_voll.domain.agendamento.ConsultaRepository;
import br.com.passos.med_voll.domain.agendamento.DadosAgendamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteConsultaNoDia implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacientePossuiConsultaNoMesmoHorario = consultaRepository.existsByPacienteIdAndDataBetween(
            dados.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiConsultaNoMesmoHorario){
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse horário");
        }
    }
}
