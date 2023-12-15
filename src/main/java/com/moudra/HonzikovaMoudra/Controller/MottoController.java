package com.moudra.HonzikovaMoudra.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moudra.HonzikovaMoudra.Model.Motto;
import com.moudra.HonzikovaMoudra.Service.MottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MottoController {

    @Autowired
    private MottoService mottoService;

    // Create a new motto
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createMotto(@RequestBody String motto) throws JsonProcessingException {
        return mottoService.createMotto(motto);
    }

    // Get all mottos
    @GetMapping("/all")
    public List<String> getAllMottos() {
        return mottoService.getAllMottos();
    }

    // Get random motto
    @GetMapping("/random")
    public String getRandomMotto() {
        return mottoService.getRandomMotto();
    }

    // Get motto by ID
    @GetMapping("/{id}")
    public Optional<Motto> getMottoById(@PathVariable Long id) {
        return mottoService.getMottoById(id);
    }

    // Get polite greetings
    @GetMapping("/")
    public String defaultGreetings() {
        return "Heil SS Division Wallonien";
    }

    // Delete motto by id
    @DeleteMapping("/{id}")
    public String deleteMottoById(@PathVariable Long id) {
        return mottoService.deleteMottoById(id);
    }

    // Update motto by id
    @PutMapping("/{id}")
    public String updateMottoById(@PathVariable Long id, @RequestBody String updatedMotto) throws JsonProcessingException {
        return mottoService.updateMottoById(id, updatedMotto);
    }
}
