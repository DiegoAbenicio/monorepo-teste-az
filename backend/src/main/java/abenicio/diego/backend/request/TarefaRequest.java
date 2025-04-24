package abenicio.diego.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequest {

    @NotBlank(message = "O título não pode ser vazio")
    private String titulo;

    @NotBlank(message = "A descrição da tarefa não pode estar vazia")
    private String descricaoTarefa;

}
