package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.AlbumRec;
import com.company.musicstorerecommendations.repository.AlbumRecRepository;
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
@WebMvcTest(AlbumRecController.class)
public class AlbumRecControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRecRepository repo;

    @Autowired
    private ObjectMapper mapper;

    AlbumRec inputAlbumRec;

    AlbumRec outputAlbumRec;

    AlbumRec updateAlbumRec;

    AlbumRec incorrectUpdateRec;

    List<AlbumRec> outputList;

    @Before
    public void setUp() throws Exception {
        inputAlbumRec = new AlbumRec();
        inputAlbumRec.setAlbumId(1);
        inputAlbumRec.setLiked(true);
        inputAlbumRec.setUserId(1);

        outputAlbumRec = new AlbumRec(1,1,1,true);
        updateAlbumRec = new AlbumRec(1,1,1,false);
        incorrectUpdateRec = new AlbumRec(500,1,1,false);
        outputList = new ArrayList<>(Arrays.asList(outputAlbumRec));

        doReturn(Optional.of(outputAlbumRec)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID Input")).when(repo).save(incorrectUpdateRec);
    }

    @Test
    public void shouldAddNewAlbumOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputAlbumRec);

        mockMvc.perform(post("/albumrec")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllAlbumRecsOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/albumrec"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }


}
