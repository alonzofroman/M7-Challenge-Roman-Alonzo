package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.BadIdException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@Valid @RequestBody Track track) {
        return repo.save(track);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getTracks() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable int id) {
        Optional<Track> optionalTrack = repo.findById(id);
        if (optionalTrack.isPresent() == false) {
            throw new BadIdException("There is no track with the path Id");
        }
        return optionalTrack.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@PathVariable int id, @RequestBody Track track) {
        if (track.getId() == null) {
            track.setId(id);
        }
        else if (track.getId() != id) {
            throw new BadIdException("The ID in your path and the ID in your request body do not match.");
        }
        repo.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTrack(@PathVariable int id) {
        repo.deleteById(id);
    }
}
