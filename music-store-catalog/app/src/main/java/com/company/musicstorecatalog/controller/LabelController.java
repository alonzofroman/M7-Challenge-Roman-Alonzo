package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@Valid @RequestBody Label label) {
        return repo.save(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getLabels() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable int id) {
        Optional<Label> optionalLabel = repo.findById(id);
        if (optionalLabel.isPresent() == false) {
            throw new BadIdException("There is no label with that Id.");
        }
        return optionalLabel.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable int id, @RequestBody Label label) {
        if (label.getId() == null) {
            label.setId(id);
        }
        else if (label.getId() != id) {
            throw new BadIdException("The label id in your path does not match the id in your request body.");
        }
        repo.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLabel(@PathVariable int id) {
         repo.deleteById(id);
    }
}
