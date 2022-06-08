package com.company.musicstorerecommendations.controller;


import com.company.musicstorerecommendations.exception.NonMatchingIdException;
import com.company.musicstorerecommendations.model.LabelRec;
import com.company.musicstorerecommendations.repository.LabelRecRepository;
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
@WebMvcTest(LabelRecController.class)
public class LabelRecControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRecRepository repo;

    @Autowired
    private ObjectMapper mapper;

    LabelRec inputLabelRec;
    LabelRec outputLabelRec;
    LabelRec updateLabelRec;
    LabelRec incorrectUpdateRec;

    List<LabelRec> outputList;

    @Before
    public void setUp() throws Exception {
        inputLabelRec = new LabelRec();
        inputLabelRec.setLabelId(1);
        inputLabelRec.setLiked(false);
        inputLabelRec.setUserId(1);

        outputLabelRec = new LabelRec(1,1,1,false);
        updateLabelRec = new LabelRec(1,1,1,true);
        incorrectUpdateRec = new LabelRec(500, 1,1,false);
        outputList = new ArrayList<>(Arrays.asList(
                outputLabelRec
        ));

        doReturn(outputLabelRec).when(repo).save(inputLabelRec);
        doReturn(Optional.of(outputLabelRec)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
//        doReturn(outputLabelRec).when(repo).findById(1);
        doThrow(new NonMatchingIdException("Incorrect Id input")).when(repo).save(incorrectUpdateRec);
    }

    @Test
    public void shouldAddNewLabelRecOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputLabelRec);

        mockMvc.perform(post("/labelrec")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllLabelRecsOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/labelrec"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneLabelRecOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputLabelRec);

        mockMvc.perform(get("/labelrec/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateLabelRecOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(updateLabelRec);
        mockMvc.perform(put("/labelrec/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelRecOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/labelrec/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectUpdateRec);
        mockMvc.perform(put("/labelrec/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
