package com.example.SpringBoot_Angular.services;

import com.example.SpringBoot_Angular.exceptions.DataIntegrityViolationException;
import com.example.SpringBoot_Angular.exceptions.ObjectnotFoundException;
import com.example.SpringBoot_Angular.model.dtos.TecnicoDto;
import com.example.SpringBoot_Angular.model.entity.Pessoa;
import com.example.SpringBoot_Angular.model.entity.Tecnico;
import com.example.SpringBoot_Angular.repositories.PessoaRepository;
import com.example.SpringBoot_Angular.repositories.TecnicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDto objDto) {
        objDto.setId(null);
        objDto.setSenha(encoder.encode(objDto.getSenha()));
        validaPorCpfEEmail(objDto);
        Tecnico newObj = new Tecnico(objDto);
        return repository.save(newObj);
    }

    private void validaPorCpfEEmail(TecnicoDto objDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no Sistema!");
        }
        obj = pessoaRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no Sistema!");
        }
    }

    public Tecnico update(Integer id, @Valid TecnicoDto objDto) {
        objDto.setId(id);
        Tecnico oldObj = findById(id);

        if (!objDto.getSenha().equals(oldObj.getSenha()))
            objDto.setSenha(encoder.encode(objDto.getSenha()));

        validaPorCpfEEmail(objDto);
        oldObj = new Tecnico(objDto);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (!obj.getChamados().isEmpty()){
            throw new DataIntegrityViolationException("Técnico possui ordens de Serviço e não pode ser apagado!");
        }
            repository.deleteById(id);
    }
}
