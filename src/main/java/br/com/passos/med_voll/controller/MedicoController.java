package br.com.passos.med_voll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.passos.med_voll.medico.DadosAtualizacaoMedico;
import br.com.passos.med_voll.medico.DadosCadastroMedico;
import br.com.passos.med_voll.medico.DadosListagemMedico;
import br.com.passos.med_voll.medico.Medico;
import br.com.passos.med_voll.medico.MedicoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(
            @PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.findById(dados.id()).orElseThrow();
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();
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
