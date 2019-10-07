package com.galvanize.simplegitarapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GuitarController.class)
public class GuitarConrollerTest {//4

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuitarService guitarService;

    ObjectMapper mapper = new ObjectMapper();
    /**
     * getGuitarByModel
     * @throws Exception
     */
    @Test
    public void getGuitarByModel_shouldReturnsGitarDetails() throws Exception{
        Guitar guitar = new Guitar("Guild","D45Bld", 7);
        guitar.setId(3l);
        given(guitarService.getSelectedGuitarByModel(anyString())).willReturn(guitar);

        mockMvc.perform(get("/guitars/model/c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("model").value("Guild"))
                .andExpect(jsonPath("brand").value("D45Bld"))
                .andExpect(jsonPath("strings").value(7))
                .andExpect(jsonPath("id").value(3l));
    }


    @Test
    public void getGuitar_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarByModel(anyString())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(get("/guitars/model/Guild"))
                .andExpect(status().isNotFound());
    }


    /**
     * getGuitarById
     * @throws Exception
     */
    @Test
    public void getGuitarById_shouldReturnsGitarDetails() throws Exception{
        Guitar guitar = new Guitar("Guild","D45Bld", 7);
        guitar.setId(3l);
        given(guitarService.getSelectedGuitarById(anyLong())).willReturn(guitar);
        mockMvc.perform(get("/guitars/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("model").value("Guild"))
                .andExpect(jsonPath("brand").value("D45Bld"))
                .andExpect(jsonPath("strings").value(7))
                .andExpect(jsonPath("id").value(3l));
    }

    @Test
    public void getGuitarById_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarById(anyLong())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(get("/guitars/3"))
                .andExpect(status().isNotFound());
    }

    /**
     * getAllGitars
     * @throws Exception
     */

   @Test
    public void getAllGitars_shouldReturnAllGitarsDetailsFromDB() throws Exception{
       List<Guitar> guitarList = new ArrayList<>();
       Guitar guitar = new Guitar("Guild", "D45Bld",7);
       Guitar guitar1 = new Guitar("Guild2", "D45Bld2",14);
       guitarList.add(guitar);
       guitarList.add(guitar1);
        given(guitarService.getAllGuitarGitarsDetails()).willReturn(guitarList);
       MvcResult result = mockMvc.perform(get("/guitars"))
                .andExpect(status().isOk())
               .andDo(print())
               .andReturn();
       String content = result.getResponse().getContentAsString();
       assertEquals(content,"[{\"id\":null,\"model\":\"Guild\",\"brand\":\"D45Bld\",\"strings\":7},{\"id\":null,\"model\":\"Guild2\",\"brand\":\"D45Bld2\",\"strings\":14}]");
   }

    /**
     * createNewGitar
     * @throws Exception
     */
    @Test
    public void addGitar_shouldReturnGitarDetailsFromDB() throws Exception{
        Guitar inputGuitar = new Guitar("Guild","D45Bld", 7);

        //Object to JSON in String
        String inputJson = mapper.writeValueAsString(inputGuitar);

        Guitar outputGuitar = new Guitar("Guild","D45Bld", 7);
        outputGuitar.setId(7l);

        //Object to JSON in String
        String outputJson = mapper.writeValueAsString(outputGuitar);
        given(guitarService.addNewGuitarInstance(any(Guitar.class))).willReturn(outputGuitar);

        this.mockMvc.perform(post("/guitars")
                .characterEncoding("utf-8")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void addGitar_shouldrReturnNullException() throws Exception{
        given(guitarService.getSelectedGuitarById(anyLong())).willThrow(new GuitarNotFoundException());
        mockMvc.perform(post("/guitars")
                .characterEncoding("utf-8")
                .content(" ")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

    /**
     * update Guitar
     * @throws Exception
     */
    @Test
    public void updateGitar_shouldrReturngitarDetails() throws Exception{

        Guitar outputGuitar = new Guitar("Guild","D45Bld", 7);
        outputGuitar.setId(7l);
        //Object to JSON in String
        String inputJson = mapper.writeValueAsString(outputGuitar);
        given(guitarService.updateSelectedGuitarById(anyLong(), any(Guitar.class))).willReturn(outputGuitar);

        MvcResult result = mockMvc.perform(put("/guitars/7")
                .characterEncoding("utf-8")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(inputJson, content);
    }

    @Test
    public void updateGitar_shouldrReturnNullException() throws Exception{
        Guitar outputGuitar = new Guitar("Guild","D45Bld", 7);
        outputGuitar.setId(7l);
        //Object to JSON in String
        String firstParam = "{\"id\":7},";
        String inputJson = firstParam + mapper.writeValueAsString(outputGuitar);
        System.out.println(inputJson);
        given(guitarService.updateSelectedGuitarById(anyLong(), any(Guitar.class))).willThrow(new GuitarNotFoundException());
        mockMvc.perform(post("/guitars/7")
                .characterEncoding("utf-8")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError());
    }

}
