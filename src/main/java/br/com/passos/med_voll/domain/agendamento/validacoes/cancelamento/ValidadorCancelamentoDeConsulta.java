package br.com.passos.med_voll.domain.agendamento.validacoes.cancelamento;

import br.com.passos.med_voll.domain.agendamento.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
