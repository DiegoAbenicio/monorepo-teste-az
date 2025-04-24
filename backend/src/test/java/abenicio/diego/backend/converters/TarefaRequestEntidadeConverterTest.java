package abenicio.diego.backend.converters;

import abenicio.diego.backend.dados.TarefaMok;
import abenicio.diego.backend.dto.TarefaDTO;
import abenicio.diego.backend.entity.Tarefa;
import abenicio.diego.backend.request.TarefaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TarefaRequestEntidadeConverterTest {

    @InjectMocks
    private TarefaRequestEntidadeConverter tarefaRequestEntidadeConverter;

    @DisplayName("Deve alternar os dados da tarefa e permanecer com o id e concluida igual")
    @Test
    void devePermanecerComOMesmoIdEConcluida(){
        Tarefa tarefaMok = TarefaMok.getTarefaInconcluida();
        TarefaRequest request = new TarefaRequest("Alterando Texto", "Nova descrição");

        Tarefa tarefa = tarefaRequestEntidadeConverter.converterRequestParaEntidade(request, tarefaMok);

        Assertions.assertNotNull(tarefa);
        Assertions.assertEquals(tarefa.getDescricaoTarefa(), request.getDescricaoTarefa());
        Assertions.assertEquals(tarefa.getTitulo(), request.getTitulo());
        Assertions.assertEquals(tarefaMok.getId(), tarefa.getId());
        Assertions.assertEquals(tarefaMok.getConcluida(), tarefa.getConcluida());
    }

    @DisplayName("Deve criar uma tarefa com os dados da Request")
    @Test
    void devePopular(){
        TarefaRequest request = new TarefaRequest("Alterando Texto", "Nova descrição");

        Tarefa tarefa = tarefaRequestEntidadeConverter.converterRequestParaEntidade(request, new Tarefa());

        Assertions.assertNotNull(tarefa);
        Assertions.assertEquals(tarefa.getDescricaoTarefa(), request.getDescricaoTarefa());
        Assertions.assertEquals(tarefa.getTitulo(), request.getTitulo());
        Assertions.assertFalse(tarefa.getConcluida());
    }

}
