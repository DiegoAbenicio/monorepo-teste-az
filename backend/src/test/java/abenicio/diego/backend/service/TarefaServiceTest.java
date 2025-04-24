package abenicio.diego.backend.service;

import abenicio.diego.backend.converters.TarefaRequestEntidadeConverter;
import abenicio.diego.backend.dados.TarefaMok;
import abenicio.diego.backend.dto.TarefaDTO;
import abenicio.diego.backend.entity.Tarefa;
import abenicio.diego.backend.repository.TarefaRepository;
import abenicio.diego.backend.request.TarefaRequest;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaRequestEntidadeConverter tarefaRequestEntidadeConverter;

    @DisplayName("Deve concluir uma tarefa")
    @Test
    void deveConcluirUmaTarefa(){
        Tarefa tarefaMok = TarefaMok.getTarefaInconcluida();
        Mockito.when(tarefaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tarefaMok));
        Mockito.when(tarefaRepository.save(Mockito.any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaDTO tarefaDTO = tarefaService.concluirTarefa(1L);

        Assertions.assertNotNull(tarefaDTO);
        Assertions.assertTrue(tarefaDTO.getRealizada());
        Assertions.assertEquals(tarefaMok.getId(), tarefaDTO.getId());
    }

    @DisplayName("Deve desmarcar como concluida uma tarefa")
    @Test
    void deveDesmarcarConcluidaUmaTarefa(){
        Tarefa tarefaMok = TarefaMok.getTarefaConcluida();
        Mockito.when(tarefaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tarefaMok));
        Mockito.when(tarefaRepository.save(Mockito.any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaDTO tarefaDTO = tarefaService.concluirTarefa(1L);

        Assertions.assertNotNull(tarefaDTO);
        Assertions.assertFalse(tarefaDTO.getRealizada());
        Assertions.assertEquals(tarefaMok.getId(), tarefaDTO.getId());
    }

    @DisplayName("Deve retornar uma TarefaDTO igual a tarefa da requisição e com Id")
    @Test
    void deveCriarTarefa() {
        TarefaRequest request = new TarefaRequest("Tarefa de Teste 2", "Qualquer descrição");
        Tarefa tarefaMok = TarefaMok.getTarefaInconcluida();
        Mockito.when(tarefaRepository.save(Mockito.any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(tarefaRequestEntidadeConverter.converterRequestParaEntidade(Mockito.any(TarefaRequest.class), Mockito.any(Tarefa.class))).thenReturn(tarefaMok);

        TarefaDTO dto = tarefaService.criarTarefa(request);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(request.getTitulo(), dto.getTitulo());
        Assertions.assertEquals(request.getDescricaoTarefa(), dto.getDescricaoTarefa());
        Assertions.assertFalse(dto.getRealizada());
        Assertions.assertNotNull(dto.getId());
    }


    @DisplayName("Verifica se esta chamando o metodo com o Id certo")
    @Test
    void deveDeletarTarefa() {
        Mockito.doNothing().when(tarefaRepository).deleteById(Mockito.anyLong());
        tarefaService.deletarTarefa(1L);
        Mockito.verify(tarefaRepository).deleteById(1L);
    }


}
