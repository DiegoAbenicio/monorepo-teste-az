import Vue from 'vue';
import Router from 'vue-router';
import PaginaTarefa from '@/views/PaginaTarefa.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'PaginaTarefas',
      component: PaginaTarefa,
    },
  ],
});
