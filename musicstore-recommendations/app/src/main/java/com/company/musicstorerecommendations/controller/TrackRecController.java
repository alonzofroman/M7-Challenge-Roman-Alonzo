package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.BadIdException;
import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.TrackRec;
import com.company.musicstorerecommendations.repository.TrackRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="trackrec")
public class TrackRecController {

    @Autowired
    private TrackRecRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRec createTrackRec(@Valid @RequestBody TrackRec trackRec) {
        return repo.save(trackRec);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRec> getTrackRecs() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRec getTrackRecById(@PathVariable int id) {
        Optional<TrackRec> optionalTrackRec = repo.findById(id);
        if (optionalTrackRec.isPresent() == false) {
            throw new BadIdException("There is no track recommendation with the path Id");
        }
        return optionalTrackRec.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRec(@PathVariable int id, @RequestBody TrackRec trackRec) {
        if (trackRec.getId() == null) {
            trackRec.setId(id);
        }
        else if (trackRec.getId() != id) {
            throw new NonMatchingIdException("The ID in your path and the ID in your request body do not match.");
        }
        repo.save(trackRec);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTrackRec(@PathVariable int id) {
        repo.deleteById(id);
    }
}
