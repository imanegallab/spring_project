package com.gsnotes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsnotes.bo.Element;
import com.gsnotes.services.IElementService;
import com.gsnotes.web.importexcel.repository.ElementRepository;

@Service
@Transactional
public class ElementServiceImpl implements IElementService {
	@Autowired
	private ElementRepository elemrepo;
	
	public Element getbyidMatiere(long id) {
		 Optional<Element> IA=elemrepo.findById( id);
      if(IA.isPresent()) {
    	 return  IA.get();
     }
		return null;
		
	}

	
	 public List<Element> getAllElement(){
		  return elemrepo.findAll();  }

}
