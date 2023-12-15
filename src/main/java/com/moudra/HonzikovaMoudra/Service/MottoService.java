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
import java.util.Random;

// V Spring frameworku jsou anotace @Component, @Bean a @Service používány pro označení tříd,
// které mají být spravovány kontejnerem IoC (Inversion of Control).
// Každá z těchto anotací má specifické použití a kontext.

// @Component
// je obecná anotace používaná k označení jakékoli třídy jako komponenty.
// Používá se, když není k dispozici žádná specifická anotace pro konkrétní typ komponenty (např. service, repository, controller).
// Používá se pro obecné komponenty, které nemají specifický účel.

// @Service
// je specializovaná forma @Component a používá se pro označení tříd, které poskytují business logiku nebo služby.
// Používá se pro vrstvy servisní logiky.
// Je často používána v kombinaci s business servisními třídami.

// @Bean
// se používá na metody v konfiguračních třídách nebo v třídách označených @Configuration.
@Service
public class MottoService {

    ObjectMapper objectMapper = new ObjectMapper();
    // https://www.baeldung.com/jackson-object-mapper-tutorial

    @Autowired
    // Anotace @Autowired zajistí automatické vložení instance MottoRepository do této třídy (injektuje závislost).
    private MottoRepository mottoRepository;

    public List<String> getAllMottos() {
        var mottos = mottoRepository.findAll();
        List<String> list = new ArrayList<>();
        for (Motto motto : mottos) {
            list.add("Honzíkovo moudro č. " + motto.getId() + ": " + motto.getText());
        }
        return list;
    }

    // @Transactional
    // Tato anotace označuje metodu jako transakční.
    // V rámci Spring frameworku to znamená, že tato metoda bude provedena jako jedna transakce.
    // Pokud dojde k nějaké chybě během provádění metody, transakce bude zrušena
    // a všechny změny provedené v rámci této transakce budou vráceny do původního stavu.

    // objectMapper je instance třídy ObjectMapper z knihovny Jackson,
    // která se používá k mapování JSON dat na objekty a obráceně.

    // Metoda readValue je použita k deserializaci (čtení) JSON řetězce a vytvoření objektu typu Motto.

    // Celkově tato metoda přijímá JSON reprezentaci motto, převádí ji na objekt typu Motto a pak tento objekt ukládá do databáze.
    // Metoda je označena jako transakční, což zajišťuje konzistenci dat v případě neúspěchu nebo chyby během operace.
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

    @Transactional
    public String updateMottoById(Long id, String updatedMotto) throws JsonProcessingException {
        Optional<Motto> oldMotto = mottoRepository.findById(id);

        if (oldMotto.isPresent()) {
            Motto newMotto = objectMapper.readValue(updatedMotto, Motto.class);
            mottoRepository.save(newMotto);
            return "Motto with ID " + id + " updated successfully.";
        } else {
            throw new RuntimeException("Motto with ID " + id + " not found.");
        }
    }
}
