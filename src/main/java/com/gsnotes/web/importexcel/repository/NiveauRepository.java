package com.gsnotes.web.importexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Niveau;
import com.gsnotes.bo.Utilisateur;

public interface NiveauRepository extends JpaRepository<Niveau, Long> {
	public Niveau getByAlias(String Alias);

	
	
}
