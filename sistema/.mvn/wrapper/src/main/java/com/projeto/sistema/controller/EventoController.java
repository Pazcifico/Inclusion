package com.projeto.sistema.controller;

import com.projeto.sistema.model.Evento;
import com.projeto.sistema.repository.EventoRepository;
import com.projeto.sistema.service.EventoService;

import java.util.List;  // Importação correta
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
    

    // Exibir eventos criados pelo usuário
    @GetMapping("/meus")
    public String listarEventosCriados(@RequestParam("usuarioId") Long usuarioId, Model model) {
        List<Evento> eventos = eventoService.buscarEventosCriados(usuarioId);
        model.addAttribute("eventosCriados", eventos);
        return "eventos-criados"; // A view será renderizada com a lista de eventos
    }

    // Exibir eventos nos quais o usuário está inscrito
    @GetMapping("/inscritos")
    public String listarEventosInscritos(@RequestParam("usuarioId") Long usuarioId, Model model) {
        model.addAttribute("eventos", eventoService.buscarEventosInscritos(usuarioId));
        return "eventos-inscritos";
    }
}
