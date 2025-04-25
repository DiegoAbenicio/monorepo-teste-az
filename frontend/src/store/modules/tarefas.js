import { listarTarefas } from "@/service/TarefaService";
import Swal from "sweetalert2";

export default {
  namespaced: true,
  state: {
    tarefas: [],
    totalPaginas: 0,
    paginaAtual: 1,
    tarefaSelecionadaId: null,
  },
  mutations: {
    SET_TAREFAS(state, payload) {
      state.tarefas = payload.content;
      state.totalPaginas = payload.totalPages;
    },
    SET_PAGINA_ATUAL(state, pagina) {
      state.paginaAtual = pagina;
    },
    SET_TAREFA_SELECIONADA_ID(state, id) {
        state.tarefaSelecionadaId = id;
    },
    LIMPAR_TAREFA_SELECIONADA(state) {
        state.tarefaSelecionadaId = null;
    }
  },
  actions: {
    async carregarTarefas({ commit }, { pagina = 1, titulo = "", statusFiltro = "TODAS" }) {
        commit("SET_TAREFAS", []);
        const params = {
          page: pagina - 1,
          size: 9,
          titulo: titulo || undefined,
          apenasInconcluidas: false,
          apenasConcluidas: false,
        };

        if (statusFiltro === "APENAS CONCLUIDAS") {
          params.apenasConcluidas = true;
        } else if (statusFiltro === "APENAS INCONCLUIDAS") {
          params.apenasInconcluidas = true;
        }
        listarTarefas(params)
            .then((response) => {
                commit("SET_TAREFAS", response.data);
                commit("SET_PAGINA_ATUAL", pagina);
            }).catch((error) => {
                Swal.fire("Erro ao carregar tarefas", error, "error");
            })
    }
  },
  getters: {
    tarefas: state => state.tarefas,
    totalPaginas: state => state.totalPaginas,
    paginaAtual: state => state.paginaAtual,
    tarefaSelecionadaId: state => state.tarefaSelecionadaId,
  },

};
