package com.gsnotes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Utilisateur;
import com.gsnotes.services.IUtilisateurService;
import com.gsnotes.web.importexcel.repository.NiveauRepository;
import com.gsnotes.web.importexcel.repository.utilisateurrepository;

@Service
@Transactional
public class UtilisateurServiceImpl implements IUtilisateurService {
	@Autowired
	private utilisateurrepository rp;
	 public com.gsnotes.bo.Utilisateur getbyNom(String Nom) {
		  com.gsnotes.bo.Utilisateur m=rp.getByNom(Nom);
		return m;
		  
	  }

}
