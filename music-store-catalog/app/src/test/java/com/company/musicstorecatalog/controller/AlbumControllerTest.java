package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.NonMatchingIdException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.*;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRepository repo;

    @Autowired
    private ObjectMapper mapper;

    Album inputAlbum;
    Album outputAlbum;
    Album updateAlbum;
    Album incorrectAlbum;
    List<Album> outputList;

    @Before
    public void setUp() throws Exception {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 23);
        Date inputDate = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2012);
        cal2.set(Calendar.MONTH, Calendar.MARCH);
        cal2.set(Calendar.DAY_OF_MONTH, 23);
        Date updateDate = cal2.getTime();

        inputAlbum = new Album();
        inputAlbum.setArtistId(1);
        inputAlbum.setLabelId(1);
        inputAlbum.setlistPrice(new BigDecimal(9.99));
        inputAlbum.setTitle("Blunderbuss");
        inputAlbum.setReleaseDate(inputDate);



        outputAlbum = new Album(1,"Blunderbuss",1, inputDate, 1, new BigDecimal(9.99));
        updateAlbum = new Album(1,"Blunderbuss",1, updateDate, 1, new BigDecimal(9.99));
        incorrectAlbum = new Album(500,"Blunderbuss",1, updateDate, 1, new BigDecimal(9.99));
        outputList = new ArrayList<>(Arrays.asList(outputAlbum));

        doReturn(Optional.of(outputAlbum)).when(repo).findById(1);
        doReturn(outputList).when(repo).findAll();
        doThrow(new NonMatchingIdException("Incorrect ID input")).when(repo).save(incorrectAlbum);
    }

    @Test
    public void shouldAddNewAlbumOnPostRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(inputAlbum);

        mockMvc.perform(post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetAllAlbumOnGetRequest() throws Exception {
        String outputJson = mapper.writeValueAsString(outputList);

        mockMvc.perform(get("/album"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetOneAlbumOnGetRequestById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputAlbum);

        mockMvc.perform(get("/album/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldUpdateAlbumOnPutRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(updateAlbum);

        mockMvc.perform(put("/album/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteAlbumOnDeleteRequest() throws Exception {
        mockMvc.perform(delete("/album/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422ErrorWhenIncorrectIdIsInUpdateRequest() throws Exception {
        String inputJson = mapper.writeValueAsString(incorrectAlbum);
        mockMvc.perform(put("/album/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
