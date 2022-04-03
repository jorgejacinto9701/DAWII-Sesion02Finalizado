package com.empresa.service;

import java.util.List;
import java.util.Optional;

import com.empresa.entity.Alumno;

public interface AlumnoService {

	public abstract List<Alumno> listaAlumno();
	public abstract Alumno insertaActualizaAlumno(Alumno obj);
	public abstract List<Alumno> listaAlumnoPorDni(String dni);
	public abstract List<Alumno> listaAlumnoPorDniDiferenteDelMismo(String dni, int idAlumno);
	public abstract Optional<Alumno> listaAlumnoPorId(int idAlumno);
	public abstract void eliminaPorId(int idAlumno);
	
}
