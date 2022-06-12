package com.gsnotes.web.importexcel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Module;

public interface moduleRepository extends JpaRepository<Module, Long> {

	public Module getByTitre(String titre);
	

}
