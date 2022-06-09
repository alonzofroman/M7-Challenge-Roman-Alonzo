package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.NonMatchingIdException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelRepository repo;

    @Autowired
    private ObjectMapper mapper;

    Label inputLabel;
    Label outputLabel;
    Label updateLabel;
    Label incorrectLabel;
    List<Label> outputList;

    @Before
    public void setUp() throws Exception {
        inputLabel = new Label();
        inputLabel.setName("Third Man Records");
        inputLabel.setWebsite("https://www.thirdmanrecords.com/blogs/news");

        outputLabel = new Label(1, "Third Man Records", "https://www.thirdmanrecords.com/blogs/news");
        updateLabel = new Label(1, "Third Man Records", "https://www.thirdmanrecords.com/");
        incorrectLabel = new Label(500, "Third Man Records", "https://www.thirdmanrecords.com/");
        outputList = new ArrayList<>(Arrays.asList(outputLabel));

        doReturn(Optional.of(outputLabel)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input")).when(repo).save(incorrectLabel);
    }

    @Test
    public void shouldAddNewLabelOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputLabel);

        mockMvc.perform(post("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllLabelsAsAListOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/label"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneLabelOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputLabel);

        mockMvc.perform(get("/label/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateLabelOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(updateLabel);

        mockMvc.perform(put("/label/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteLabelOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/label/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInUpdateRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectLabel);
        mockMvc.perform(put("/label/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
