package com.gsnotes.services;

import java.util.List;

public interface IModuleService {
	 public List<com.gsnotes.bo.Module> getAllModule();
	 public List<com.gsnotes.bo.Module> getAll();
	 public com.gsnotes.bo.Module getbytitre(String titre);
}
