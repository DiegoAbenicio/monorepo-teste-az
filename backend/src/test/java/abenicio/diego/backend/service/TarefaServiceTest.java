package abenicio.diego.backend.service;

import abenicio.diego.backend.converters.TarefaRequestEntidadeConverter;
import abenicio.diego.backend.dados.TarefaMok;
import abenicio.diego.backend.dto.TarefaDTO;
import abenicio.diego.backend.entity.Tarefa;
import abenicio.diego.backend.repository.TarefaRepository;
import abenicio.diego.backend.request.FiltrosTarefas;
import abenicio.diego.backend.request.TarefaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @DisplayName("Deve fazer uma edição retornar uma TarefaDTO que permanessa com o id e com o campo concluida igual, mas que altera os dados do titulo e da descrição")
    @Test
    void deveEditarTarefa() {
        TarefaRequest request = new TarefaRequest("Tarefa de Teste 2", "Qualquer descrição");
        Tarefa tarefaMok = TarefaMok.getTarefaInconcluida();

        Mockito.when(tarefaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tarefaMok));
        Mockito.when(tarefaRequestEntidadeConverter.converterRequestParaEntidade(Mockito.any(TarefaRequest.class), Mockito.any(Tarefa.class))).thenReturn(tarefaMok);
        Mockito.when(tarefaRepository.save(Mockito.any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TarefaDTO dto = tarefaService.editarTarefa(2L, request);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(2L, dto.getId());
        Assertions.assertEquals(request.getTitulo(), dto.getTitulo());
        Assertions.assertEquals(request.getDescricaoTarefa(), dto.getDescricaoTarefa());
        Assertions.assertFalse(dto.getRealizada());
    }

    @DisplayName("Verifica se esta chamando o metodo com o Id certo")
    @Test
    void deveDeletarTarefa() {
        Mockito.doNothing().when(tarefaRepository).deleteById(Mockito.anyLong());
        tarefaService.deletarTarefa(1L);
        Mockito.verify(tarefaRepository).deleteById(1L);
    }

    @DisplayName("Deve lançar exception com o retorno vazio")
    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        Mockito.when(tarefaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> tarefaService.buscarTarefaDTOPorId(1L));
    }

    @DisplayName("Deve retornar o DTO")
    @Test
    void deveRetornarDTO() {
        Tarefa tarefaMok = TarefaMok.getTarefaInconcluida();
        Mockito.when(tarefaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tarefaMok));

        TarefaDTO dto = tarefaService.buscarTarefaDTOPorId(1L);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getId());
        Assertions.assertEquals(tarefaMok.getId(), dto.getId());
        Assertions.assertEquals(tarefaMok.getTitulo(), dto.getTitulo());
        Assertions.assertEquals(tarefaMok.getDescricaoTarefa(), dto.getDescricaoTarefa());
        Assertions.assertEquals(tarefaMok.getConcluida(), dto.getRealizada());

    }

    @Test
    @DisplayName("Deve buscar tarefas filtrando por título e apenas concluídas")
    void deveBuscarTarefasComFiltroDeTituloEConcluidas() {
        Pageable pageable = PageRequest.of(0, 10);
        FiltrosTarefas filtros = new FiltrosTarefas("Tarefa", true, false);

        Tarefa tarefaConcluida = TarefaMok.getTarefaConcluida();

        Page<Tarefa> pageMock = new PageImpl<>(List.of(tarefaConcluida));

        Mockito.when(tarefaRepository.buscarTarefas(
                filtros.getTitulo(),
                filtros.getApenasConcluidas(),
                filtros.getApenasInconcluidas(),
                pageable
        )).thenReturn(pageMock);

        Page<TarefaDTO> resultado = tarefaService.buscarTarefas(filtros, pageable);

        Assertions.assertEquals(1, resultado.getTotalElements());
        Assertions.assertEquals("Tarefa de Teste", resultado.getContent().get(0).getTitulo());
        Assertions.assertTrue(resultado.getContent().get(0).getRealizada());
    }

}
