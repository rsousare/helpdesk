package com.example.SpringBoot_Angular.services;

import com.example.SpringBoot_Angular.exceptions.ObjectnotFoundException;
import com.example.SpringBoot_Angular.model.dtos.ChamadoDto;
import com.example.SpringBoot_Angular.model.entity.Chamado;
import com.example.SpringBoot_Angular.model.entity.Cliente;
import com.example.SpringBoot_Angular.model.entity.Tecnico;
import com.example.SpringBoot_Angular.model.enums.Prioridade;
import com.example.SpringBoot_Angular.model.enums.Status;
import com.example.SpringBoot_Angular.repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {
    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDto objDto) {
        return repository.save(newChamado(objDto));
    }

    private Chamado newChamado(ChamadoDto obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());

        Chamado chamado = new Chamado();
        if (obj.getId() != null){
            chamado.setId(obj.getId());
        }

        if (obj.getStatus().equals(2)){
            chamado.setDataFecho(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }

    public Chamado update(Integer id, ChamadoDto objDto) {
        objDto.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDto);
        return repository.save(oldObj);
    }
}
