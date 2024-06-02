package mx.unam.dgtic.service;

import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService implements IEstadoService{

    @Autowired
    EstadoRepository repositorioEstado;

    @Override
    public Estado findByEstado(String estado) {
        return repositorioEstado.findByEstado(estado);
    }
}
