package com.gsnotes.services;

import java.util.List;

import com.gsnotes.bo.Etudiant;

public interface IEtudiantService {
	public com.gsnotes.bo.Etudiant getbyCne(String Cne);
	public List<Etudiant> getAllEtudiant();

}
