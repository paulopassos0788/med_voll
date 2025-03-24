package br.com.passos.med_voll.domain.paciente;

import br.com.passos.med_voll.domain.endereco.Endereco;

public record DetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf,
                                   Boolean ativo, Endereco endereco) {

    public DetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(),
                paciente.getAtivo(), paciente.getEndereco());
    }
}
