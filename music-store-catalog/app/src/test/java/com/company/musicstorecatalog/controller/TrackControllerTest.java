package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.NonMatchingIdException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRepository repo;

    @Autowired
    private ObjectMapper mapper;

    Track inputTrack;
    Track outputTrack;
    Track updateTrack;
    Track incorrectTrack;
    List<Track> outputList;

    @Before
    public void setUp() throws Exception {
        inputTrack = new Track();
        inputTrack.setTitle("Freedom At 21");
        inputTrack.setAlbumId(1);
        inputTrack.setRunTime(3);

        outputTrack = new Track(1, 1, "Freedom At 21", 3);
        updateTrack = new Track (1,1,"Freedom At 21", 4);
        incorrectTrack= new Track (500,1,"Freedom At 21", 4);
        outputList = new ArrayList<>(Arrays.asList(outputTrack));

        doReturn(Optional.of(outputTrack)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input")).when(repo).save(incorrectTrack);
    }

    @Test
    public void shouldAddNewTrackOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputTrack);

        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllTracksAsAListOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/track"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneTracktOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputTrack);

        mockMvc.perform(get("/track/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateTrackOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(updateTrack);

        mockMvc.perform(put("/track/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/track/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInUpdateRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectTrack);
        mockMvc.perform(put("/track/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
