<template>
  <v-dialog v-model="dialog" max-width="600px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="#092334" dark v-bind="attrs" v-on="on" @click="iniciarNovaTarefa">
        <v-icon left>mdi-plus</v-icon> Adicionar Tarefa
      </v-btn>
    </template>
    <v-card>
      <v-card-title class="headline">{{ tarefa.id ? 'Editar Tarefa' : 'Nova Tarefa' }}</v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="formValido">
          <v-text-field
            v-model="tarefa.titulo"
            label="Título"
            :rules="[ v => !!v || 'Título é obrigatório', v => v.length <= 20 || 'Máximo de 20 caracteres']"
            maxlength="20"
            solo
            color="#092334"
            required
          />
          <v-textarea
            v-model="tarefa.descricaoTarefa"
            label="Descrição"
            :rules="[v => !!v || 'Descrição é obrigatória', v => v.length <= 255 || 'Máximo de 255 caracteres']"
            maxlength="255"
            solo
            color="#092334"
            required
          />
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="dialog = false">Cancelar</v-btn>
        <v-spacer />
        <v-btn color="#092334" dark @click="salvarTarefa">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { criarTarefa, editarTarefa, buscarPorId } from "@/service/TarefaService";
import { mapGetters } from "vuex";
import Swal from 'sweetalert2';

export default {
  name: "ModalCriarTarefa",
  data() {
    return {
      dialog: false,
      formValido: false,
      tarefa: {
        id: null,
        titulo: "",
        descricaoTarefa: "",
        realizada: false,
      }
    };
  },
  computed: {
    ...mapGetters("tarefas", ["tarefaSelecionadaId"])
  },
  watch: {
    dialog(val) {
      if (val) this.verificarIdSelecionado();
    }
  },
  methods: {
    async verificarIdSelecionado() {
      if (this.tarefaSelecionadaId) {
        buscarPorId(this.tarefaSelecionadaId)
          .then((res) => {
            this.tarefa = { ...res.data };
          })
      } else {
        this.resetarFormulario();
      }
    },
    async salvarTarefa() {
      const isValido = this.$refs.form.validate();
      if (!isValido) return;
      if (this.tarefa.id) {
        editarTarefa(this.tarefa.id, this.tarefa)
        .then(() => {
          Swal.fire("Editado com sucesso!", "", "success");
          this.resetarFormulario();
          this.$emit("atualizar");
        })
        .catch(() => {
          Swal.fire("Erro ao editar a tarefa!", "", "error");
        })
      } else {
        criarTarefa(this.tarefa)
        .then(() => {
          Swal.fire("Criado com sucesso!", "", "success");
          this.resetarFormulario();
          this.$emit("atualizar");
        })
        .catch(() => {
          Swal.fire("Erro ao criar a tarefa!", "", "error");
        })
      }
      this.dialog = false;
    },
    resetarFormulario() {
      this.tarefa = {
        id: null,
        titulo: "",
        descricaoTarefa: "",
        realizada: false,
      };
    },
    iniciarNovaTarefa(){
      this.$store.commit("tarefas/LIMPAR_TAREFA_SELECIONADA");
      this.resetarFormulario();
    }

  }
};
</script>