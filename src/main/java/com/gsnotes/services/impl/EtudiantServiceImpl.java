package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.services.IEtudiantService;
import com.gsnotes.web.importexcel.repository.EtudiantRepository;

@Service
@Transactional
public class EtudiantServiceImpl implements IEtudiantService{
	@Autowired
	private EtudiantRepository etd;
	
	 public com.gsnotes.bo.Etudiant getbyCne(String Cne) {
		  com.gsnotes.bo.Etudiant m=etd.getByCne(Cne);
		return m;
		  
	  }
	 public List<Etudiant> getAllEtudiant(){
		  return etd.findAll();  }
}
