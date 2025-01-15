package com.example.SpringBoot_Angular.services;

import com.example.SpringBoot_Angular.exceptions.DataIntegrityViolationException;
import com.example.SpringBoot_Angular.exceptions.ObjectnotFoundException;
import com.example.SpringBoot_Angular.model.dtos.ClienteDto;
import com.example.SpringBoot_Angular.model.entity.Pessoa;
import com.example.SpringBoot_Angular.model.entity.Cliente;
import com.example.SpringBoot_Angular.repositories.PessoaRepository;
import com.example.SpringBoot_Angular.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objecto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDto objDto) {
        objDto.setId(null);
        objDto.setSenha(encoder.encode(objDto.getSenha()));
        validaPorCpfEEmail(objDto);
        Cliente newObj = new Cliente(objDto);
        return repository.save(newObj);
    }

    private void validaPorCpfEEmail(ClienteDto objDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no Sistema!");
        }
        obj = pessoaRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no Sistema!");
        }
    }

    public Cliente update(Integer id, ClienteDto objDto) {
        objDto.setId(id);
        Cliente oldObj = findById(id);

        if (!objDto.getSenha().equals(oldObj.getSenha()))
            objDto.setSenha(encoder.encode(objDto.getSenha()));

        validaPorCpfEEmail(objDto);
        oldObj = new Cliente(objDto);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (!obj.getChamados().isEmpty()){
            throw new DataIntegrityViolationException("Cliente possui ordens de Serviço e não pode ser apagado!");
        }
            repository.deleteById(id);
    }
}
