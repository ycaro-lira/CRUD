package com.praticando.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.praticando.entidade.Usuario;



@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Long>{

}
