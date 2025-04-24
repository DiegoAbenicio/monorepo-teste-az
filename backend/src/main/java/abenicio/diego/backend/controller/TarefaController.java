package abenicio.diego.backend.controller;

import abenicio.diego.backend.dto.TarefaDTO;
import abenicio.diego.backend.request.FiltrosTarefas;
import abenicio.diego.backend.request.TarefaRequest;
import abenicio.diego.backend.service.TarefaService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "backend-tarefas")
public class TarefaController {
    private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);

    private final TarefaService tarefaService;

    @Operation(summary = "Busca uma tarefa do banco de dados utilizando o id.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar tarefa."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarTarefa(@PathVariable("id") Long idTarefa){
        logger.info("TarefaController.buscarTarefa()");
        return new ResponseEntity<>(tarefaService.buscarTarefaDTOPorId(idTarefa), HttpStatus.OK);
    }

    @Operation(summary = "Busca todas as tarefas cadastradas.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar tarefas."),
    })
    @GetMapping
    public ResponseEntity<Page<TarefaDTO>> buscarTarefas(FiltrosTarefas filtrosTarefas, Pageable pageable){
        logger.info("TarefaController.buscarTarefas()");
        return new ResponseEntity<>(tarefaService.buscarTarefas(filtrosTarefas, pageable), HttpStatus.OK);
    }

    @Operation(summary = "Cadastra uma nova tarefa.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa cadastrada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar tarefa."),
    })
    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody TarefaRequest request){
        logger.info("TarefaController.criarTarefa()");
        return new ResponseEntity<>(tarefaService.criarTarefa(request), HttpStatus.OK);
    }

    @Operation(summary = "Edita uma tarefa ja cadastrada.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa editada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro ao editar tarefa."),
    })
    @PutMapping("/{id}/editar")
    public ResponseEntity<TarefaDTO> editarTarefa(@PathVariable("id") Long idTarefa, @Valid @RequestBody TarefaRequest request){
        logger.info("TarefaController.editarTarefa()");
        return new ResponseEntity<>(tarefaService.editarTarefa(idTarefa, request), HttpStatus.OK);
    }

    @Operation(summary = "Sinaliza uma tarefa como concluída.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa concluída com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro ao concluir a tarefa."),
    })
    @PutMapping("/{id}/concluir")
    public ResponseEntity<TarefaDTO> concluirTarefa(@PathVariable("id") Long idTarefa){
        logger.info("TarefaController.concluirTarefa()");
        return new ResponseEntity<>(tarefaService.concluirTarefa(idTarefa), HttpStatus.OK);
    }

    @Operation(summary = "Erro ao deletar uma tarefa.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar a tarefa."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable("id") Long idTarefa){
        logger.info("TarefaController.deletarTarefa()");
        tarefaService.deletarTarefa(idTarefa);
        return new ResponseEntity<>("Tarefa deletada com sucesso", HttpStatus.OK);
    }

}
