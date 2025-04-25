import { shallowMount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import createVuetifyInstance from '../setup'
import ModalCriarTarefa from '@/components/modal/ModalCriarTarefa.vue'

const localVue = createLocalVue()
localVue.use(Vuex)

describe('ModalCriarTarefa.vue', () => {
  let vuetify
  let store
  let tarefasModule

  beforeEach(() => {
    vuetify = createVuetifyInstance()

    tarefasModule = {
      namespaced: true,
      getters: {
        tarefaSelecionadaId: () => null
      },
      mutations: {
        LIMPAR_TAREFA_SELECIONADA: jest.fn()
      }
    }

    store = new Vuex.Store({
      modules: {
        tarefas: tarefasModule
      }
    })
  })

  it('Renderiza corretamente os botões do card', () => {
    const wrapper = shallowMount(ModalCriarTarefa, {
      localVue,
      vuetify,
      store
    })

    expect(wrapper.text()).toContain('Nova Tarefa   Cancelar  Salvar')
  })

  it('Deve chamar o vuex para limpar o id selecionando quando iniciar uma nova tarefa', async () => {
    const wrapper = shallowMount(ModalCriarTarefa, {
      localVue,
      vuetify,
      store
    })

    await wrapper.vm.$emit('iniciarNovaTarefa')

    if (typeof wrapper.vm.iniciarNovaTarefa === 'function') {
      wrapper.vm.iniciarNovaTarefa()
    }

    expect(tarefasModule.mutations.LIMPAR_TAREFA_SELECIONADA).toHaveBeenCalled()
  })
})
