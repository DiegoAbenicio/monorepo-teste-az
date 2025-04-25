<template>
  <v-card :class="['card', { 'status-ativo': tarefaLocal.realizada }]" elevation="3">
    <v-card-title class="title">{{ tarefaLocal.titulo }}</v-card-title>
    <v-card-text class="texto-card">{{ tarefaLocal.descricaoTarefa }}</v-card-text>
    <v-card-actions class="pa-3 pt-0">
      <v-checkbox
        v-model="tarefaLocal.realizada"
        hide-details
        @click="alterarStatus(tarefaLocal.id)"
        density="compact"
        label="Status"
        color="success"
        class="ma-0 pa-0"
        :readonly="true"
      />
      <v-spacer />
      <v-btn icon size="small" color="#092334" variant="text" @click="editar(tarefaLocal.id)">
        <v-icon size="18">mdi-eye</v-icon>
      </v-btn>
      <v-btn icon size="small" color="#092334" variant="text" @click="deletar(tarefaLocal.id)">
        <v-icon size="18">mdi-delete</v-icon>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { concluirTarefa, deletarTarefa } from '@/service/TarefaService';
import Swal from 'sweetalert2';

export default {
  name: "TarefaCard",
  props: {
    tarefa: {
      type: Object,
      required: true
    }
  },
  data(){
    return{
      tarefaLocal: {
        id: this.tarefa.id,
        titulo: this.tarefa.titulo,
        descricaoTarefa: this.tarefa.descricaoTarefa,
        realizada: this.tarefa.realizada
      }
    }
  },
  methods: {
    alterarStatus(id){
      concluirTarefa(id)
      .then((response) => {
        this.tarefaLocal.realizada = response.data.realizada;
      })
    },
    deletar(id){
      Swal.fire({
        title: "Deseja relmente deletar?",
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "Deletar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#EA3131",
      }).then((result) => {
        if (result.isConfirmed) {
          deletarTarefa(id)
          .then(() => {
            Swal.fire("Deletado!", "", "success");
            this.$emit("atualizar");
          })
          .catch(() => {
            Swal.fire("Erro ao deletar", "", "error");
          })
        }
      });
    },
    editar(id) {
      this.$store.commit("tarefas/SET_TAREFA_SELECIONADA_ID", id);
      this.$emit("abrirModal");
    },
  }
};
</script>


<style scoped>
.card {
  width: 300px;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
  transition: background-color 0.3s ease;
}

.status-ativo {
  background-color: #e6f4ea;
}

.title {
  font-size: 0.85rem;
  font-weight: 600;
  line-height: 1.1rem;
  height: 35px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
}

.texto-card {
  font-size: 0.9rem;
  line-height: 1.2rem;
  height: 75px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}

</style>

