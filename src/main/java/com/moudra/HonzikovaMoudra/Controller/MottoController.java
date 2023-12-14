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

    // Get motto by ID
    @GetMapping("/{id}")
    public Optional<Motto> getMottoById(@PathVariable Long id) {
        return mottoService.getMottoById(id);
    }

    @GetMapping("/")
    public String defaultGreetings() {
        return "Heil SS Division Wallonien";
    }
}
