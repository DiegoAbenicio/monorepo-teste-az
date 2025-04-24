package abenicio.diego.backend.service;

import abenicio.diego.backend.converters.TarefaRequestEntidadeConverter;
import abenicio.diego.backend.dto.TarefaDTO;
import abenicio.diego.backend.entity.Tarefa;
import abenicio.diego.backend.repository.TarefaRepository;
import abenicio.diego.backend.request.FiltrosTarefas;
import abenicio.diego.backend.request.TarefaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    private final TarefaRequestEntidadeConverter tarefaRequestEntidadeConverter;

    public Page<TarefaDTO> buscarTarefas(FiltrosTarefas filtrosTarefas, Pageable pageable){
        Page<Tarefa> tarefasPage = tarefaRepository.buscarTarefas(filtrosTarefas.getTitulo(),
                                                                  filtrosTarefas.getApenasConcluidas(),
                                                                  filtrosTarefas.getApenasInconcluidas(),
                                                                  pageable);
        return new PageImpl<>(tarefasPage.stream().map(TarefaDTO::new).collect(Collectors.toList()));
    }

    public TarefaDTO criarTarefa(TarefaRequest request){
        Tarefa tarefa = tarefaRequestEntidadeConverter.converterRequestParaEntidade(request, new Tarefa());
        return new TarefaDTO(tarefaRepository.save(tarefa));
    }

    public TarefaDTO editarTarefa(Long idTarefa, TarefaRequest request){
        Tarefa tarefa = buscarTarefaPorId(idTarefa);
        tarefa = tarefaRequestEntidadeConverter.converterRequestParaEntidade(request, tarefa);
        return new TarefaDTO(tarefaRepository.save(tarefa));
    }

    public TarefaDTO buscarTarefaDTOPorId(Long id){
        return new TarefaDTO(buscarTarefaPorId(id));
    }

    private Tarefa buscarTarefaPorId(Long id){
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Tarefa de id: (%s) não localizada", id)));
    }

    public TarefaDTO concluirTarefa(Long id){
        Tarefa tarefa = buscarTarefaPorId(id);
        tarefa.setConcluida(!tarefa.getConcluida());
        return new TarefaDTO(tarefaRepository.save(tarefa));
    }

    public void deletarTarefa(Long id){
        tarefaRepository.deleteById(id);
    }

}
