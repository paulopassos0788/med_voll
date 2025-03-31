package br.com.passos.med_voll.domain.agendamento;

public class ValidacaoException extends RuntimeException {

    public ValidacaoException(String mensagem) {
        super(mensagem);
    }

    public ValidacaoException() {
        super("Erro de validação");
    }
}
