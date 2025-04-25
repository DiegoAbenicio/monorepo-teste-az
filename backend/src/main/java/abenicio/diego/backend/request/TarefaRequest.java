package abenicio.diego.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequest {

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 20, message = "O título deve ter no máximo 20 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição da tarefa não pode estar vazia")
    @Size(max = 255, message = "A descrição da tarefa deve ter no máximo 255 caracteres")
    private String descricaoTarefa;

}
