package br.com.passos.med_voll.domain.medico;

import br.com.passos.med_voll.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(

        @NotNull Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {

}
