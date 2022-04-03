package com.empresa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.entity.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

	@Query("select e from Alumno e where e.dni = ?1")
	public List<Alumno> listaPorDni(String dni);
	
	@Query("select e from Alumno e where e.dni = ?1 and e.idAlumno <> ?2")
	public List<Alumno> listaPorDniDiferenteSiMismo(String dni, int idAlumno);
	


}
