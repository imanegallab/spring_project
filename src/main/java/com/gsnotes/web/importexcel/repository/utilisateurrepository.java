package com.gsnotes.web.importexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Utilisateur;



public interface utilisateurrepository extends JpaRepository<Utilisateur, Long>{
	public Utilisateur getByNom(String nom);
}
