package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.LabelRec;
import com.company.musicstorerecommendations.repository.LabelRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelrec")
public class LabelRecController {

    @Autowired
    private LabelRecRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRec createLabelRec(@Valid @RequestBody LabelRec labelRec) {
        return repo.save(labelRec);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRec> getLabels() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRec getLabelRecById(@PathVariable int id) {
        Optional<LabelRec> optionalLabelRec = repo.findById(id);
        if (optionalLabelRec.isPresent() == false) {
            throw new BadIdException("There is no label recommendation with that Id.");
        }
        return optionalLabelRec.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRec(@PathVariable int id, @RequestBody LabelRec labelRec) {
        if (labelRec.getId() == null) {
            labelRec.setId(id);
        }
        else if (labelRec.getId() != id) {
            throw new NonMatchingIdException("The label id in your path does not match the id in your request body.");
        }
        repo.save(labelRec);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLabelRec(@PathVariable int id) {
        repo.deleteById(id);
    }
}
