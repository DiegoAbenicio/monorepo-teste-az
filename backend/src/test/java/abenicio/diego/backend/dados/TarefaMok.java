package abenicio.diego.backend.dados;

import abenicio.diego.backend.entity.Tarefa;

public class TarefaMok {

    public static Tarefa getTarefaConcluida() {
        return Tarefa.builder()
                .id(1L)
                .titulo("Tarefa de Teste")
                .descricaoTarefa("Descrição qualquer")
                .concluida(true)
                .build();
    }

    public static Tarefa getTarefaInconcluida() {
        return Tarefa.builder()
                .id(2L)
                .titulo("Tarefa de Teste 2")
                .descricaoTarefa("Qualquer descrição")
                .concluida(false)
                .build();
    }

}
