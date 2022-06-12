package com.gsnotes.web.importexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Niveau;


public interface InscriptionAnnuelleRepository extends JpaRepository<InscriptionAnnuelle, Long> {

	InscriptionAnnuelle getByNiveauAndEtudiant(Niveau nv, Etudiant etd);
	

}
