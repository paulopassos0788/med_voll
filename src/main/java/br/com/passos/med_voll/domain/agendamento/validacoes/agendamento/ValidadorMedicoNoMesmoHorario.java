package br.com.passos.med_voll.domain.agendamento.validacoes.agendamento;

import br.com.passos.med_voll.domain.agendamento.ConsultaRepository;
import br.com.passos.med_voll.domain.agendamento.DadosAgendamentoConsulta;
import br.com.passos.med_voll.domain.agendamento.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoNoMesmoHorario implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if(medicoPossuiConsultaNoMesmoHorario){
            throw new ValidacaoException("Médico já possui uma consulta agendada nesse horário");
        }
    }
}
