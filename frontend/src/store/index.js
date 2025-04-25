import Vue from 'vue';
import Vuex from 'vuex';
import tarefas from './modules/tarefas';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    tarefas,
  },
});
