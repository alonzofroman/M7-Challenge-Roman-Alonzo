package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.ArtistRec;
import com.company.musicstorerecommendations.repository.ArtistRecRepository;
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
@WebMvcTest(ArtistRecController.class)
public class ArtistRecControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRecRepository repo;

    @Autowired
    private ObjectMapper mapper;

    ArtistRec inputRec;
    ArtistRec outputRec;
    ArtistRec updateRec;
    ArtistRec incorrectRec;
    List<ArtistRec> outputList;

    @Before
    public void setUp() throws Exception {
        inputRec = new ArtistRec();
        inputRec.setArtistId(1);
        inputRec.setLiked(true);
        inputRec.setUserId(1);

        outputRec = new ArtistRec(1,1,1,true);
        updateRec = new ArtistRec(1,1,1,false);
        incorrectRec = new ArtistRec(500,1,1,false);
        outputList = new ArrayList<>(Arrays.asList(outputRec));

        doReturn(Optional.of(outputRec)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input")).when(repo).save(incorrectRec);
    }

    @Test
    public void shouldAddNewArtistRecOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputRec);

        mockMvc.perform(post("/artistrec")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllArtistRecsOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/artistrec"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneArtistRecOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputRec);

        mockMvc.perform(get("artistrec/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateArtistRecOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputRec);

        mockMvc.perform(put("/artistrec/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteArtistRecOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/artistrec/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInPutRequest() throws Exception {

    }

}
