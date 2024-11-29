package com.projeto.sistema.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuarioCriador")
    private List<Evento> eventosCriados = new ArrayList<>();

    @ManyToMany(mappedBy = "listClientes")
    private List<Evento> eventosInscritos = new ArrayList<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Evento> getEventosCriados() {
        return eventosCriados;
    }

    public void setEventosCriados(List<Evento> eventosCriados) {
        this.eventosCriados = eventosCriados;
    }

    public List<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    public void setEventosInscritos(List<Evento> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }
}
