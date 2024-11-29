package com.projeto.sistema.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

@PostMapping("/cadastro")
public String cadastrarUsuario(@RequestParam("nome") String nome,
                               @RequestParam("email") String email,
                               @RequestParam("confirmEmail") String confirmEmail, // Adicionado o parâmetro de confirmação de e-mail
                               @RequestParam("senha") String senha,
                               @RequestParam("confirmSenha") String confirmSenha,
                               Model model) {
    // Validação de e-mail
    if (!email.equals(confirmEmail)) {
        model.addAttribute("erro", "Os e-mails não coincidem!");
        return "cadastro"; // Retorna para a página de cadastro com erro
    }

    // Validação de senha
    if (!senha.equals(confirmSenha)) {
        model.addAttribute("erro", "As senhas não coincidem!");
        return "cadastro"; // Retorna para a página de cadastro com erro
    }

    // Verifica se o e-mail já está cadastrado
    if (usuarioRepository.existsByEmail(email)) {
        model.addAttribute("erro", "E-mail já cadastrado!");
        return "cadastro"; // Retorna para a página de cadastro com erro
    }

    // Criação do novo usuário
    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    usuario.setEmail(email);
    usuario.setSenha(senha); // Aqui seria interessante criptografar a senha antes de salvar no banco
    usuarioRepository.save(usuario);

    // Redireciona para a página de login após o cadastro
    return "redirect:/login";
}




}

