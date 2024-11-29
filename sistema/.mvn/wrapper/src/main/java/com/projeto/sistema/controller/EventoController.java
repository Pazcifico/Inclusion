package com.projeto.sistema.controller;

import com.projeto.sistema.model.Evento;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repository.EventoRepository;
import com.projeto.sistema.repository.UsuarioRepository;  // Adicione essa linha
import com.projeto.sistema.service.EventoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;  // Adicione a injeção do UsuarioRepository

    // Tela para criar um novo evento
    @GetMapping("/criar")
    public String formCriarEvento() {
        return "criar-evento";
    }

    @PostMapping("/criar")
    public String criarEvento(@ModelAttribute Evento evento, @RequestParam("usuarioId") Long usuarioId) {
        eventoService.cadastrarEvento(evento, usuarioId);
        return "redirect:/usuario/perfil"; // Redireciona para a página de perfil do usuário
    }

    // Inscrever usuário em evento
    @PostMapping("/{eventoId}/inscrever")
    public String inscreverEvento(@PathVariable Long eventoId, @CookieValue("usuarioId") Long usuarioId) {
        eventoService.inscreverUsuario(eventoId, usuarioId);
        return "redirect:/evento/inscritos"; // Redireciona para eventos inscritos
    }

    // Tela para editar evento
    @GetMapping("/editar/{eventoId}")
    public String formEditarEvento(@PathVariable Long eventoId, Model model) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        model.addAttribute("evento", evento);
        return "editar-evento"; // Página de edição
    }

    @PostMapping("/editar/{eventoId}")
    public String editarEvento(@PathVariable Long eventoId, @ModelAttribute Evento eventoAtualizado) {
        eventoService.atualizarEvento(eventoId, eventoAtualizado);
        // Garantindo que o id do usuário seja passado corretamente na URL
        return "redirect:/evento/meus?usuarioId=" + eventoAtualizado.getUsuarioCriador().getId();
    }

    @GetMapping("/meus")
    public String listarEventosCriados(@RequestParam("usuarioId") Long usuarioId, Model model) {
        List<Evento> eventos = eventoService.buscarEventosCriados(usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("eventosCriados", eventos);  // Passa os eventos para o template
        model.addAttribute("usuario", usuario);        // Passa o usuário para o template
        return "eventos-criados"; // Página de eventos criados
    }

    // Exibir eventos nos quais o usuário está inscrito
    @GetMapping("/inscritos")
    public String listarEventosInscritos(@RequestParam("usuarioId") Long usuarioId, Model model) {
        model.addAttribute("eventos", eventoService.buscarEventosInscritos(usuarioId));
        return "eventos-inscritos";
    }
}
