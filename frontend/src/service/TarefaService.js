import api from "./api";

export const listarTarefas = (params) => {
    return api.get("/tarefas", { params });
};

export const buscarPorId = (id) => {
    return api.get(`/tarefas/${id}`);
};

export const concluirTarefa = (id) => {
    return api.put(`/tarefas/${id}/concluir`,);
};

export const deletarTarefa = (id) => {
    return api.delete(`/tarefas/${id}`);
};

export const criarTarefa = (tarefa) => {
    return api.post(`/tarefas`, tarefa);
};


export const editarTarefa = (id, tarefa) => {
    return api.put(`/tarefas/${id}/editar`, tarefa);
};
