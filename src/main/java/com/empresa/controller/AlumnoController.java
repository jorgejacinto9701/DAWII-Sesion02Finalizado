package com.empresa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

@RestController
@RequestMapping("/rest/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoService service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumno() {
		List<Alumno> lista = service.listaAlumno();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> insertaAlumno(@RequestBody Alumno obj) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			List<Alumno> lstAlumnos = service.listaAlumnoPorDni(obj.getDni());
			if (CollectionUtils.isEmpty(lstAlumnos)) {
				obj.setIdAlumno(0);
				Alumno objSalida = service.insertaActualizaAlumno(obj);
				if (objSalida == null) {
					salida.put("mensaje", "Error en el registro ");
				}else {
					salida.put("mensaje", "Registro exitoso");
				}
			}else {
				salida.put("mensaje", "El DNI ya existe : " + obj.getDni());
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el registro " + e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
	}

	
	@PutMapping
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> actualizaAlumno(@RequestBody Alumno obj) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Alumno> optional =  service.listaAlumnoPorId(obj.getIdAlumno());
			if (optional.isPresent()) {
				List<Alumno> lstAlumno = service.listaAlumnoPorDniDiferenteDelMismo(obj.getDni(), obj.getIdAlumno());
				if (CollectionUtils.isEmpty(lstAlumno)) {
					Alumno objSalida = service.insertaActualizaAlumno(obj);
					if (objSalida == null) {
						salida.put("mensaje", "Error en actualizar ");
					}else {
						salida.put("mensaje", "Actualización exitosa");
					}
				}else {
					salida.put("mensaje", "El DNI ya existe : " + obj.getDni());
				}
			}else {
				salida.put("mensaje", "El ID no existe : " + obj.getIdAlumno());
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la actualización " + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}

	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> eliminaAlumno(@PathVariable int id) {
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
			Optional<Alumno> optional =  service.listaAlumnoPorId(id);
			if (optional.isPresent()) {
				service.eliminaPorId(id);
				salida.put("mensaje", "Eliminación exitosa");
			}else {
				salida.put("mensaje", "El ID no existe : " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en la eliminación " + e.getMessage());
		}
		return ResponseEntity.ok(salida);
	}
	
	@GetMapping("/dni/{dni}")
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumnoPorDni(@PathVariable String dni) {
		List<Alumno> lista = service.listaAlumnoPorDni(dni);
		return ResponseEntity.ok(lista);
	}
}



