package mx.unam.dgtic.service;

import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Estado;

import java.util.List;

public interface IEstadoService {
    Estado findByEstado(String estado);
}
