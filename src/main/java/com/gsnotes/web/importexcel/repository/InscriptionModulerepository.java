package com.gsnotes.web.importexcel.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.gsnotes.bo.InscriptionAnnuelle;

import com.gsnotes.bo.InscriptionModule;



public interface InscriptionModulerepository extends JpaRepository<InscriptionModule, Long>{

	InscriptionModule getByInscriptionAnnuelleAndModule(InscriptionAnnuelle IA,com.gsnotes.bo.Module module);



}
