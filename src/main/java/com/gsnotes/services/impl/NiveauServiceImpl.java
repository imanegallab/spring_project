package com.gsnotes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.services.INiveauService;
import com.gsnotes.web.importexcel.repository.NiveauRepository;

@Service
@Transactional
public class NiveauServiceImpl implements INiveauService{
	@Autowired
	private NiveauRepository niveaurepo;
	public com.gsnotes.bo.Niveau getbyAlias(String titre) {
		  com.gsnotes.bo.Niveau m=niveaurepo.getByAlias(titre);
		return m;
		  
	  }
}
