package com.gsnotes.web.importexcel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionMatiere;
import com.gsnotes.bo.Module;

public interface InscriptionMatiereRepository  extends JpaRepository<InscriptionMatiere, Long>{

	public InscriptionMatiere getByInscriptionAnnuelleAndMatiere(InscriptionAnnuelle etd, Element module);
	
}
