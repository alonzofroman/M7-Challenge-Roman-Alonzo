package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.exception.NonMatchingIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/album")
public class AlbumController {
    @Autowired
    AlbumRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@Valid @RequestBody Album album) {
       return repo.save(album);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAlbums() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable int id) {
        Optional<Album> optionalAlbum = repo.findById(id);
        if (optionalAlbum.isPresent() == false) {
            throw new BadIdException("There is no album with the id " + id);
        }
        return optionalAlbum.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable int id, @RequestBody Album album) {
        if (album.getId() == null) {
            album.setId(id);
        } if (album.getId() != id) {
            throw new NonMatchingIdException("The Id in your path does not match the id, " + id + "in the request body, " + album.getId());
        }
        repo.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlbum(@PathVariable int id) {
        repo.deleteById(id);
    }
}
