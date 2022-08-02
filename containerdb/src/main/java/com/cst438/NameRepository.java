package com.cst438;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository <Name, Integer> {
	
	public List<Name> findAll();

}
