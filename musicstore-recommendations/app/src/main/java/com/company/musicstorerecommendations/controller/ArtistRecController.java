package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.ArtistRec;
import com.company.musicstorerecommendations.repository.ArtistRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "artistrec")
public class ArtistRecController {

    @Autowired
    private ArtistRecRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRec createArtistRec(@Valid @RequestBody ArtistRec artistRec) {
        return repo.save(artistRec);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRec> getArtistRecs() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRec getArtistRecById(@PathVariable int id) {
        Optional<ArtistRec> optionalArtistRec = repo.findById(id);
        if (optionalArtistRec.isPresent() == false) {
            throw new BadIdException("There is no artist recommendation with that Id");
        }
        return optionalArtistRec.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRec(@PathVariable int id, @RequestBody ArtistRec artistRec) {
        if (artistRec.getId() == null) {
            artistRec.setId(id);
        } else if (artistRec.getId() != id) {
            throw new NonMatchingIdException("The id in your path is different from the id in your body");
        }

        repo.save(artistRec);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtistRec(@PathVariable int id) {
        repo.deleteById(id);
    }
}
