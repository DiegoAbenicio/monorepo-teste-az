package abenicio.diego.backend.dto;


import abenicio.diego.backend.entity.Tarefa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaDTO {

    private Long id;
    private String titulo;
    private String descricaoTarefa;
    private Boolean realizada;

    public TarefaDTO(Tarefa entidade){
        if(Objects.isNull(entidade))
            return;
        setId(entidade.getId());
        setTitulo(entidade.getTitulo());
        setDescricaoTarefa(entidade.getDescricaoTarefa());
        setRealizada(entidade.getConcluida());
    }

}
