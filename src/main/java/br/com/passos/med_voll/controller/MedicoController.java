package br.com.passos.med_voll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.passos.med_voll.domain.medico.DadosAtualizacaoMedico;
import br.com.passos.med_voll.domain.medico.DadosCadastroMedico;
import br.com.passos.med_voll.domain.medico.DadosDetalhamentoMedico;
import br.com.passos.med_voll.domain.medico.DadosListagemMedico;
import br.com.passos.med_voll.domain.medico.Medico;
import br.com.passos.med_voll.domain.medico.MedicoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable pageable) {
        var medicos = repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
        return ResponseEntity.status(HttpStatus.OK).body(medicos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.findById(dados.id()).orElseThrow();
        medico.atualizarInformacoes(dados);
        var detalhamento = new DadosDetalhamentoMedico(medico);
        return ResponseEntity.status(HttpStatus.OK).body(detalhamento);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoMedico(medico));
    }

    /*
     * @DeleteMapping("/{id}")
     * 
     * @Transactional
     * public void excluir(@PathVariable Long id) {
     * repository.deleteById(id);
     * }
     * 
     */

}
