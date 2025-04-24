package abenicio.diego.backend.converters;

import abenicio.diego.backend.entity.Tarefa;
import abenicio.diego.backend.request.TarefaRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TarefaRequestEntidadeConverter {

    public Tarefa converterRequestParaEntidade(TarefaRequest request, Tarefa tarefa){
        if(Objects.isNull(tarefa.getId())){
            tarefa.setConcluida(false);
        }
        tarefa.setDescricaoTarefa(request.getDescricaoTarefa());
        tarefa.setTitulo(request.getTitulo());
        return tarefa;
    }


}
