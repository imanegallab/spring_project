package com.gsnotes.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Module;
import com.gsnotes.services.IModuleService;
import com.gsnotes.web.importexcel.repository.ElementRepository;
import com.gsnotes.web.importexcel.repository.moduleRepository;
@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {
	@Autowired
	private moduleRepository modulerepo;
	 public List<com.gsnotes.bo.Module> getAllModule(){
		  return modulerepo.findAll();
	  }
	 public List<com.gsnotes.bo.Module> getAll(){
		  return modulerepo.findAll();
	  }
	  public com.gsnotes.bo.Module getbytitre(String titre) {
		  com.gsnotes.bo.Module m=modulerepo.getByTitre(titre);
		return m;
		  
	  }
	

}
