package com.gsnotes.web.importexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
  public Etudiant getByCne(String Cne);


}
