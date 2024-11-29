package com.projeto.sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;  // Usando LocalDateTime
import java.util.ArrayList;
import java.util.List;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String local;
    private String detalhes;  // Descrição adicional do evento
    private String ingresso;  // Tipo de ingresso, por exemplo, gratuito ou pago
    private String tipoDeEvento;  // Exemplo: palestra, workshop, seminário

    @Column(name = "data")
    private LocalDateTime data;  // Alterado para LocalDateTime

    @ManyToMany
    @JoinTable(
        name = "evento_usuario",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> listClientes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_criador_id")
    private Usuario usuarioCriador;  // O usuário criador do evento

    // Construtores
    public Evento() {}

    public Evento(String nome, String local, LocalDateTime data, List<Usuario> listClientes, String detalhes, String ingresso, String tipoDeEvento) {
        this.nome = nome;
        this.local = local;
        this.data = data;
        this.listClientes = listClientes;
        this.detalhes = detalhes;
        this.ingresso = ingresso;
        this.tipoDeEvento = tipoDeEvento;
    }

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public List<Usuario> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Usuario> listClientes) {
        this.listClientes = listClientes;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getIngresso() {
        return ingresso;
    }

    public void setIngresso(String ingresso) {
        this.ingresso = ingresso;
    }

    public String getTipoDeEvento() {
        return tipoDeEvento;
    }

    public void setTipoDeEvento(String tipoDeEvento) {
        this.tipoDeEvento = tipoDeEvento;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(Usuario usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    // Método para adicionar um cliente à lista de inscritos
    public void addInscrito(Usuario usuario) {
        this.listClientes.add(usuario);
    }
}
