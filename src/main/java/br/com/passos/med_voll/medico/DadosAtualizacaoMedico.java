package br.com.passos.med_voll.medico;

import br.com.passos.med_voll.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(

        @NotNull Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {

}
