<template>
  <v-container class="main-container">
    <v-row class="ml-2 mr-2">
      <v-col cols="8">
        <h1 class="titulo-pagina">Gerenciador de Tarefas</h1>
      </v-col>
      <v-col cols="4" class="d-flex justify-end align-center">
        <v-btn class="custom-btn" color="#092334" size="small" variant="text">
          <ModalCriarTarefa ref="modalCriarTarefa" @atualizar="realizarConsultaNovamente" />
        </v-btn>
      </v-col>
    </v-row>

    <v-row class="mt-2 ml-2 mr-2">
      <v-col cols="8">
        <v-text-field
          v-model="filtroTitulo"
          label="Buscar por título"
          solo
          color="#092334"
          @change="realizarConsultaNovamente"
        />
      </v-col>
      <v-col cols="4">
        <v-select
          v-model="filtroStatus"
          :items="statusOpcoes"
          label="Filtrar por status"
          solo
          color="#092334"
          @change="realizarConsultaNovamente"
        />
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="4" class="d-flex justify-center" v-for="tarefa in tarefas" :key="tarefa.id">
        <TarefaCard :tarefa="tarefa" @atualizar="realizarConsultaNovamente" @abrirModal="abrirModalCriarTarefa"/>
      </v-col>
    </v-row>
    <v-row class="mt-8">
      <v-col cols="12" class="d-flex justify-center">
        <v-pagination
          :value="paginaAtual"
          :length="totalPaginas"
          @input="trocarPagina"
          circle
          color="#092334"
          first-icon="mdi-chevron-double-left"
          last-icon="mdi-chevron-double-right"
          prev-icon="mdi-chevron-left"
          next-icon="mdi-chevron-right"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TarefaCard from "@/components/card/TarefaCard.vue";
import ModalCriarTarefa from "@/components/modal/ModalCriarTarefa.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "PaginaTarefa",
  components: {
    TarefaCard,
    ModalCriarTarefa
  },
  data() {
    return {
      filtroTitulo: "",
      filtroStatus: "TODAS",
      statusOpcoes: ["TODAS", "APENAS CONCLUIDAS", "APENAS INCONCLUIDAS"],
      tarefaKey: 0
    };
  },
  computed: {
    ...mapGetters("tarefas", ["tarefas", "paginaAtual", "totalPaginas"]),
  },
  mounted() {
    this.carregarTarefas(this.paginaAtual);
  },
  methods: {
    ...mapActions("tarefas", ["carregarTarefas"]),
    trocarPagina(pagina) {
      this.carregarTarefas({
        pagina: pagina,
        titulo: this.filtroTitulo,
        statusFiltro: this.filtroStatus
      });
    },
    realizarConsultaNovamente() {
      this.carregarTarefas({
        pagina: 1,
        titulo: this.filtroTitulo,
        statusFiltro: this.filtroStatus
      });

    },
    abrirModalCriarTarefa() {
      this.$refs.modalCriarTarefa.dialog = true;
    }
  },
};
</script>

<style scoped>
.main-container {
    height: 101vh;
    width: 1200px;
    padding: 0 10px;
    background-color: rgba(219,123,81,255);
}
</style>
