package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.TrackRec;
import com.company.musicstorerecommendations.repository.TrackRecRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecController.class)
public class TrackRecControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackRecRepository repo;

    @Autowired
    private ObjectMapper mapper;

    TrackRec inputRec;
    TrackRec outputRec;
    TrackRec updateRec;
    TrackRec incorrectRec;
    List<TrackRec> outputList;


    @Before
    public void setUp() throws Exception {
        inputRec = new TrackRec();
        inputRec.setTrackId(1);
        inputRec.setUserId(1);
        inputRec.setLiked(true);

        outputRec = new TrackRec(1,1,1,true);
        updateRec = new TrackRec(1,1,1,false);
        incorrectRec = new TrackRec(500,1,1,false);
        outputList = new ArrayList<>(Arrays.asList(outputRec));

        doReturn(Optional.of(outputRec)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input, please make sure they match.")).when(repo).save(incorrectRec);
    }

    @Test
    public void shouldAddNewTrackRecOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputRec);

        mockMvc.perform(post("/trackrec")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllTrackRecsOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/trackrec"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneTrackRecOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputRec);

        mockMvc.perform(get("/trackrec/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateTrackRecOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputRec);

        mockMvc.perform(put("/trackrec/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTrackRecOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/trackrec/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectRec);
        mockMvc.perform(put("/trackrec/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
