package com.projeto.sistema.service;

import com.projeto.sistema.model.Evento;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repository.EventoRepository;
import com.projeto.sistema.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cadastrar novo evento
    public void cadastrarEvento(Evento evento, Long usuarioId) {
        Usuario usuarioCriador = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        evento.setUsuarioCriador(usuarioCriador);
        eventoRepository.save(evento);
    }

    // Inscrever usuário em evento
    public void inscreverUsuario(Long eventoId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        evento.addInscrito(usuario); // Adiciona o usuário ao evento
        eventoRepository.save(evento); // Salva o evento atualizado
    }

    // Atualizar informações do evento
    public void atualizarEvento(Long eventoId, Evento eventoAtualizado) {
        Evento eventoExistente = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        eventoExistente.setNome(eventoAtualizado.getNome());
        eventoExistente.setLocal(eventoAtualizado.getLocal());
        eventoExistente.setData(eventoAtualizado.getData());
        eventoExistente.setDetalhes(eventoAtualizado.getDetalhes());
        eventoExistente.setIngresso(eventoAtualizado.getIngresso());
        eventoExistente.setTipoDeEvento(eventoAtualizado.getTipoDeEvento());

        eventoRepository.save(eventoExistente);
    }

    // Buscar eventos criados por um usuário
    public List<Evento> buscarEventosCriados(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getEventosCriados();
    }

    // Buscar eventos inscritos pelo usuário
    public List<Evento> buscarEventosInscritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuario.getEventosInscritos();
    }
}
