package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.AlbumRec;
import com.company.musicstorerecommendations.repository.AlbumRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/albumrec")
public class AlbumRecController {

    @Autowired
    AlbumRecRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRec createAlbumRec(@Valid @RequestBody AlbumRec albumRec) {
        return repo.save(albumRec);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRec> getAlbumRecs() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRec getAlbumRecById(@PathVariable int id) {
        Optional<AlbumRec> optionalAlbumRec = repo.findById(id);
        if (optionalAlbumRec.isPresent() == false) {
            throw new BadIdException("There is no album recommendation with the id " + id);
        }
        return optionalAlbumRec.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRec(@PathVariable int id, @RequestBody AlbumRec albumRec) {
        if (albumRec.getId() == null) {
            albumRec.setId(id);
        } else if (albumRec.getId() != id) {
            throw new NonMatchingIdException("The Id in your path does not match the id");
        }
        repo.save(albumRec);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlbumRec(@PathVariable int id) {
        repo.deleteById(id);
    }
}
