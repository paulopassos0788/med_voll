package br.com.passos.med_voll.medico;

import br.com.passos.med_voll.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone,
        Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(),
                medico.getEspecialidade(), medico.getEndereco());

    }

}