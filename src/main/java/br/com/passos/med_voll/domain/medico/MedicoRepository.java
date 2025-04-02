package br.com.passos.med_voll.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query(value = """
        select * from Medico m
        where
        m.ativo = 1
        and
        m.especialidade = :especialidade
        and
        m.id not in(
            select c.medico_id from Consulta c
            where
            c.data = :data
            and
            c.motivo_cancelamento is null
        )
        order by rand()
        limit 1
    """, nativeQuery = true)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.ativo
            from Medico m
            where m.id = :idMedico
            and m.ativo = true
            """)
    Boolean findAtivoById(Long idMedico);
}
