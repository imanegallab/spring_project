package com.gsnotes.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Enseignant;
import com.gsnotes.services.IEnseignantService;
import com.gsnotes.web.importexcel.repository.enseignantrepository;

@Service
@Transactional
public class EnseignantServiceImpl implements IEnseignantService{
	@Autowired
	private enseignantrepository epr;
	public Enseignant getbyidenseignant(long id) {
		 Optional<Enseignant> IA=epr.findById( id);
       if(IA.isPresent()) {
     	 return  IA.get();
      }
		return null;
		
	}
}
