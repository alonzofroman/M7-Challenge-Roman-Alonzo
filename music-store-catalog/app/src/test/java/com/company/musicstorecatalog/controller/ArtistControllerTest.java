package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.NonMatchingIdException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
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

import java.math.BigDecimal;
import java.util.*;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository repo;

    @Autowired
    private ObjectMapper mapper;

    Artist inputArtist;
    Artist outputArtist;
    Artist updateArtist;
    Artist incorrectArtist;
    List<Artist> outputList;

    @Before
    public void setUp() throws Exception {
        inputArtist = new Artist();
        inputArtist.setName("Jack White");
        inputArtist.setInstagram("https://www.instagram.com/officialjackwhite/?hl=en");
        inputArtist.setTwitter("https://twitter.com/thirdmanrecords");

        outputArtist = new Artist(1,"Jack White", "https://www.instagram.com/officialjackwhite/?hl=en", "https://twitter.com/thirdmanrecords");
        updateArtist = new Artist(1,"Jack White", "https://www.instagram.com/officialjackwhite/", "https://twitter.com/thirdmanrecords");
        incorrectArtist = new Artist(500,"Jack White", "https://www.instagram.com/officialjackwhite/", "https://twitter.com/thirdmanrecords");
        outputList = new ArrayList<>(Arrays.asList(outputArtist));

        doReturn(Optional.of(outputArtist)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input")).when(repo).save(incorrectArtist);
    }

    @Test
    public void shouldAddNewArtistOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputArtist);

        mockMvc.perform(post("/artist")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllArtistsAsAListOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/artist"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneArtistOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputArtist);

        mockMvc.perform(get("/artist/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateArtistOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(updateArtist);

        mockMvc.perform(put("/artist/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteArtistOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/artist/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInUpdateRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectArtist);
        mockMvc.perform(put("/artist/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
