package com.projeto.sistema.controller;

import com.projeto.sistema.model.Evento;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repository.EventoRepository;
import com.projeto.sistema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Exibe o perfil do usuário.
     *
     * @param usuarioId ID do usuário obtido através do cookie.
     * @param model     Modelo para enviar dados à view.
     * @return Página de perfil do usuário.
     */
    @GetMapping("/perfil")
    public String exibirPerfil(@CookieValue(value = "usuarioId", defaultValue = "-1") Long usuarioId, Model model) {
        if (usuarioId == -1) {
            throw new RuntimeException("Cookie 'usuarioId' não encontrado. Faça login novamente.");
        }
    
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        model.addAttribute("usuario", usuario);
        model.addAttribute("eventosCriados", usuario.getEventosCriados());
        model.addAttribute("eventosInscritos", usuario.getEventosInscritos());
    
        return "perfil";
    }
    

    /**
     * Exibe o formulário de criação de evento.
     *
     * @param usuarioId ID do usuário obtido através do cookie.
     * @param model     Modelo para enviar dados à view.
     * @return Página de formulário de criação de evento.
     */
    @GetMapping("/evento/criar")
    public String exibirFormularioCriarEvento(@CookieValue("usuarioId") Long usuarioId, Model model) {
        model.addAttribute("usuarioId", usuarioId);
        return "criar-evento";
    }

    /**
     * Processa a criação de um novo evento.
     *
     * @param usuarioId     ID do usuário obtido através do cookie.
     * @param nome          Nome do evento.
     * @param data          Data e hora do evento (formato ISO-8601).
     * @param local         Local do evento.
     * @param detalhes      Detalhes do evento.
     * @param ingresso      Informações sobre ingressos.
     * @param tipoDeEvento  Tipo do evento.
     * @return Redirecionamento para a página de perfil do usuário.
     */
    @PostMapping("/evento/criar")
public String criarEvento(@CookieValue(value = "usuarioId", defaultValue = "-1") Long usuarioId,
                          @RequestParam("nome") String nome,
                          @RequestParam("data") String data,
                          @RequestParam("local") String local,
                          @RequestParam("detalhes") String detalhes,
                          @RequestParam("ingresso") String ingresso,
                          @RequestParam("tipoDeEvento") String tipoDeEvento) {

    if (usuarioId == -1) {
        throw new RuntimeException("Cookie 'usuarioId' não encontrado. Faça login novamente.");
    }

    Usuario usuario = buscarUsuarioPorId(usuarioId);

    // Converte a data do formato String para LocalDateTime
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    LocalDateTime dataEvento = LocalDateTime.parse(data, formatter);

    // Cria e salva o novo evento
    Evento evento = new Evento();
    evento.setNome(nome);
    evento.setData(dataEvento);
    evento.setLocal(local);
    evento.setDetalhes(detalhes);
    evento.setIngresso(ingresso);
    evento.setTipoDeEvento(tipoDeEvento);
    evento.setUsuarioCriador(usuario); // Associa o evento ao criador

    eventoRepository.save(evento);

    return "redirect:/usuario/perfil";
}


    /**
     * Busca um usuário pelo ID.
     *
     * @param usuarioId ID do usuário.
     * @return Objeto Usuario encontrado.
     */
    private Usuario buscarUsuarioPorId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + usuarioId + " não encontrado."));
        
        // Log para depuração - opcional
        System.out.println("Eventos Criados: " + usuario.getEventosCriados());
    
        return usuario;
    }
    
}
