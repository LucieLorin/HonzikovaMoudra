package com.moudra.HonzikovaMoudra.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moudra.HonzikovaMoudra.Model.Motto;
import com.moudra.HonzikovaMoudra.Repository.MottoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MottoService {

    ObjectMapper objectMapper = new ObjectMapper();

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

    public String getRandomMotto() {
        Random random = new Random();
        List<String> mottos = getAllMottos();
        if (mottos.isEmpty()) {
            return "No mottos available";
        }
        return mottos.get(random.nextInt(mottos.size()));
    }

    @Transactional
    public String deleteMottoById(Long id) {
        var motto = getMottoById(id);
        if (motto.isEmpty()) {
            return "Motto with given id not found.";
        }
        mottoRepository.delete(motto.get());
        return "Motto successfully removed.";
    }

    @Modifying
    public String updateMottoById(Long id, String updatedMotto) throws JsonProcessingException {
        Optional<Motto> oldMotto = mottoRepository.findById(id);

        if (oldMotto.isPresent()) {
            Motto newMotto = objectMapper.readValue(updatedMotto, Motto.class);
            newMotto.setId(id);
            mottoRepository.save(newMotto);
            return "Motto with ID " + id + " updated successfully.";
        } else {
            throw new RuntimeException("Motto with ID " + id + " not found.");
        }
    }
}
