package com.gsnotes.web.importexcel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Element;


public interface ElementRepository extends JpaRepository<Element, Long> {
	public Element getElementByNom(String Nom);

}
