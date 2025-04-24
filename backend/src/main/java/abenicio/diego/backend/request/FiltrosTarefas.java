package abenicio.diego.backend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltrosTarefas {

    private String titulo;
    private Boolean apenasConcluidas;
    private Boolean apenasInconcluidas;

}
