package br.com.passos.med_voll.domain.agendamento;

import br.com.passos.med_voll.domain.agendamento.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import br.com.passos.med_voll.domain.medico.Medico;
import br.com.passos.med_voll.domain.medico.MedicoRepository;
import br.com.passos.med_voll.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Transactional
    public DadosDetalhamentoConsulta agendarConsulta(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Médico não encontrado");
        }

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Paciente não encontrado");
        }

        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if(medico == null) {
            throw new ValidacaoException("Não há médicos disponíveis na data informada");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var consulta = new Consulta(medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não é informado");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
