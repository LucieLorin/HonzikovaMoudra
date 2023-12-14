package com.moudra.HonzikovaMoudra.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moudra.HonzikovaMoudra.Model.Motto;
import com.moudra.HonzikovaMoudra.Repository.MottoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MottoService {

    ObjectMapper objectMapper = new ObjectMapper();
    // https://www.baeldung.com/jackson-object-mapper-tutorial

    @Autowired
    private MottoRepository mottoRepository;

    public List<String> getAllMottos() {
        var mottos = mottoRepository.findAll();
        List<String> list = new ArrayList<>();
        for (Motto motto : mottos) {
            list.add("Honzíkovo moudro č. " + motto.getId() + ": " + motto.getText());
        }
        return list;
    }

    @Transactional
    public String createMotto(String mottoInput) throws JsonProcessingException {
        Motto mottoToBeSaved = objectMapper.readValue(mottoInput, Motto.class);
        mottoRepository.save(mottoToBeSaved);
        return "Successfully saved";
    }

    public Optional<Motto> getMottoById(Long id) {
        return mottoRepository.findById(id);
    }
}
