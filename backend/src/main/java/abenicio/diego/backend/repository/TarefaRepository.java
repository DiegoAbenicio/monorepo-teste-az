package abenicio.diego.backend.repository;

import abenicio.diego.backend.entity.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t " +
            "WHERE (:titulo IS NULL OR LOWER(t.titulo) LIKE CONCAT('%', LOWER(:titulo), '%') OR :titulo = '') " +
            "AND (" +
            "   (:apenasConcluidas = TRUE AND t.concluida = TRUE) OR " +
            "   (:apenasInconcluidas = TRUE AND t.concluida = FALSE) OR " +
            "   ((:apenasConcluidas IS FALSE OR :apenasConcluidas IS NULL) AND (:apenasInconcluidas IS FALSE OR :apenasInconcluidas IS NULL))" +
            ") " +
            "ORDER BY t.id ASC")
    Page<Tarefa> buscarTarefas(@Param("titulo") String titulo,
                               @Param("apenasConcluidas") Boolean apenasConcluidas,
                               @Param("apenasInconcluidas") Boolean apenasInconcluidas,
                               Pageable pageable);


}
