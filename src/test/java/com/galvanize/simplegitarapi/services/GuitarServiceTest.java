package com.galvanize.simplegitarapi.services;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.repositories.GuitarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GuitarServiceTest {//7

    @Mock
    GuitarRepository guitarRepository;

    private GuitarService guitarService;



    @Before
    public void setUp() throws Exception {
        guitarService = new GuitarService(guitarRepository);//Injection like Autowired
    }

    /**
     * findByModel
     * @throws Exception
     */
    @Test
    public void getSelectedGuitarByModel_returnGuitarDetails() throws Exception{
        Guitar guitar = new Guitar("Guild","D45Bld", 7);
        guitar.setId(3l);
        given(guitarRepository.findByModel(anyString())).willReturn(guitar);
        guitar = guitarService.getSelectedGuitarByModel("Guild");
        assertThat(guitar.getId()).isNotNull();
        assertThat(guitar.getStrings()).isEqualTo(7);
        assertThat(guitar.getBrand()).isEqualTo("D45Bld");
        assertThat(guitar.getModel()).isEqualTo("Guild");
    }

    @Test(expected = GuitarNotFoundException.class)
    public void getSelectedGuitarByModel_whenGitarNotFound()throws Exception{
        given(guitarRepository.findByModel(anyString())).willReturn(null);
        guitarService.getSelectedGuitarByModel("Guild");
    }

    /**
     * findById
     * @throws Exception
     */
    @Test
    public void getSelectedGuitarById_returnGuitarDetails() throws Exception{
        given(guitarRepository.findById(anyLong())).willReturn(Optional.of(new Guitar("Guild","D45Bld", 7)));
        Guitar guitar = guitarService.getSelectedGuitarById(3l);
        assertThat(guitar.getId()).isNull();
        assertThat(guitar.getStrings()).isEqualTo(7);
        assertThat(guitar.getBrand()).isEqualTo("D45Bld");
        assertThat(guitar.getModel()).isEqualTo("Guild");
    }

    @Test(expected = GuitarNotFoundException.class)
    public void getSelectedGuitarById_whenGitarNotFound()throws Exception{
        given(guitarRepository.findById(anyLong())).willReturn(null);
        guitarService.getSelectedGuitarById(3l);
    }


    /**
     * findAll
     * @throws Exception
     */
    @Test
    public void getAllGuitars_returnAllGuitarsDetails() throws Exception{
        List<Guitar> guitarList = new ArrayList<>();
        Guitar guitar = new Guitar("Guild", "D45Bld",7);
        Guitar guitar1 = new Guitar("Guild2", "D45Bld2",14);
        guitarList.add(guitar);
        guitarList.add(guitar1);
        given(guitarRepository.findAll()).willReturn(guitarList);
        List<Guitar> guitarList1 = guitarService.getAllGuitarGitarsDetails();
        assertThat(guitarList1).isNotNull();
        assertThat(guitarList1).isEqualTo(guitarList);
    }

    /**
     * save one
     * @throws Exception
     */
    @Test
    public void saveGuitar_returnGuitarDetails() throws Exception{
        Guitar inputGuitar = new Guitar("Guild", "D45Bld",7);
        Guitar outputGuitar = new Guitar("Guild","D45Bld", 7);
        outputGuitar.setId(7l);
        given(guitarRepository.save(inputGuitar)).willReturn(outputGuitar);
        Guitar resultGuitar = guitarService.addNewGuitarInstance(inputGuitar);
        assertThat(resultGuitar).isNotNull();
        assertThat(resultGuitar.getId()).isEqualTo(7l);
        assertThat(resultGuitar.getModel()).contains("Guild");
        assertThat(resultGuitar.getBrand()).contains("D45Bld");
        assertThat(resultGuitar.getStrings()).isEqualTo(7);
    }

    /**
     * update exist guitar
     * @throws Exception
     */
    @Test
    public void updateGuitar_terurnGuitardetails() throws Exception{
        Guitar guitar = new Guitar("Guild", "D45Bld",7);
        guitar.setId(7l);
        given(guitarRepository.findById(anyLong())).willReturn(Optional.of(new Guitar("Guild","D45Bld", 7)));
        given(guitarRepository.save(guitar)).willReturn(guitar);
        Guitar resultGuitar = guitarService.updateSelectedGuitarById(guitar.getId(), guitar);
        assertThat(resultGuitar).isNotNull();
        assertThat(resultGuitar.getId()).isEqualTo(7l);
        assertThat(resultGuitar.getModel()).contains("Guild");
        assertThat(resultGuitar.getBrand()).contains("D45Bld");
        assertThat(resultGuitar.getStrings()).isEqualTo(7);
    }

}