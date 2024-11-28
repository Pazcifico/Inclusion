package com.projeto.sistema.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.repository.UsuarioRepository;
import com.projeto.sistema.service.CookieService;

import org.springframework.ui.Model; // Correção aqui

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // View de login
    }

    // Processar login e redirecionar
    @PostMapping("/logar")
public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
    Usuario usuarioLogado = usuarioRepository.login(usuario.getEmail(), usuario.getSenha());
    if (usuarioLogado != null) {
        // Definir cookies com caminho correto
        CookieService.setCookie(response, "usuarioId", String.valueOf(usuarioLogado.getId()), 10000);
        CookieService.setCookie(response, "nomeUsuario", usuarioLogado.getNome(), 10000);
        return "redirect:/usuario/perfil"; // Certifique-se de que esta rota exista
    }
    model.addAttribute("erro", "Usuário inválido");
    return "login";
}

}

