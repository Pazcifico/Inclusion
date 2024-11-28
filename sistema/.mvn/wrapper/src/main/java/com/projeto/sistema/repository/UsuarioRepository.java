package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.model.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    // Usa o método padrão do CrudRepository para buscar por ID
    Optional<Usuario> findById(Long id);

    // Query para autenticação de usuário
    @Query(value = "SELECT * FROM usuario WHERE email = :email AND senha = :senha", nativeQuery = true)
    Usuario login(String email, String senha);

}
