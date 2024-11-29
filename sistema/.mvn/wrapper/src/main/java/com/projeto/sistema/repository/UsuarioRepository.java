package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Usuario;
import org.springframework.lang.NonNull;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    // Query para autenticação de usuário
    @Query(value = "SELECT * FROM usuario WHERE email = :email AND senha = :senha", nativeQuery = true)
    Usuario login(@NonNull String email, @NonNull String senha);

    // Método para verificar se o e-mail já está cadastrado
    boolean existsByEmail(String email);  // Verifica a existência do e-mail
}
