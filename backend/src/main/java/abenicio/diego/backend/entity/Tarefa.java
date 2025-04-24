package abenicio.diego.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_tarefa", schema = "public")
@SequenceGenerator(name = "seq_tb_tarefa", sequenceName = "seq_tb_tarefa", allocationSize = 1)
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_tb_tarefa")
    @Column(name = "seq_tarefa")
    private Long id;

    @Column(name = "titulo")
    @NotNull
    private String titulo;

    @Column(name = "descricao_tarefa")
    private String descricaoTarefa;

    @Column(name = "concluida")
    @NotNull
    private Boolean concluida;

}
