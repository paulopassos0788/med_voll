package br.com.passos.med_voll.controller;

import br.com.passos.med_voll.paciente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        repository.save(new Paciente(dados));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(new DetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var medico = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.status(HttpStatus.OK).body(medico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.findById(dados.id()).orElseThrow();
        var detalhamento = new DetalhamentoPaciente(paciente);
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.status(HttpStatus.OK).body(detalhamento);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new DetalhamentoPaciente(paciente));
    }
}

