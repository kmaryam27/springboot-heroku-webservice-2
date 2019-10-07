package com.galvanize.simplegitarapi.repositories;

import com.galvanize.simplegitarapi.entity.Guitar;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GuitarRepositoryTest {

    /**********************************This part is just for practice jpa reacy library does not test******************************************/

    @Autowired
    private GuitarRepository guitarRepository;

    /**
     * findByModel
     * @throws Exception
     */
    @Test
    public void findByModel_returnGitarDetails() throws Exception{
        guitarRepository.deleteAll();
        Guitar savedGuitar = new Guitar("Guild","D45Bld", 7);
        savedGuitar = guitarRepository.save(savedGuitar);

        Guitar guitar = guitarRepository.findByModel("Guild");
        assertThat(guitar.getModel()).isEqualTo(savedGuitar.getModel());
        assertThat(guitar.getBrand()).isEqualTo(savedGuitar.getBrand());
        assertThat(guitar.getStrings()).isEqualTo(savedGuitar.getStrings());
    }

    /**
     * findById
     * @throws Exception
     */
    @Test
    public void findById_returnGitarDetails() throws Exception{
        Guitar savedGuitar = new Guitar();
        savedGuitar.setBrand("D45Bld");
        savedGuitar.setModel("Guild");
        savedGuitar.setStrings(7);
        savedGuitar = guitarRepository.save(savedGuitar);

        Guitar guitar = guitarRepository.findById(savedGuitar.getId()).get();
        assertThat(guitar.getModel()).isEqualTo(savedGuitar.getModel());
        assertThat(guitar.getId()).isEqualTo(savedGuitar.getId());
        assertThat(guitar.getBrand()).isEqualTo(savedGuitar.getBrand());
    }

    /**
     * getAllGuitars from db
     * @throws Exception
     */
    @Test
    public void findAll_returnGitarDetails() throws Exception{
        guitarRepository.deleteAll();
        Guitar savedGuitar = new Guitar("D45Bld", "Guild", 7);
        savedGuitar = guitarRepository.save(savedGuitar);
        List<Guitar> guitarList = guitarRepository.findAll();
        assertThat(guitarList).isNotNull();
        assertThat(guitarList.size()).isEqualTo(1);
        assertThat(guitarList.get(0).getBrand()).isEqualTo(savedGuitar.getBrand());
        assertThat(guitarList.get(0).getModel()).isEqualTo(savedGuitar.getModel());
        assertThat(guitarList.get(0).getStrings()).isEqualTo(savedGuitar.getStrings());
    }

    /**
     * addNewGuitar
     * @throws Exception
     */
    @Test
    public void save_returnGitarDetails() throws Exception{
        guitarRepository.deleteAll();
        Guitar savedGuitar = new Guitar("Guild","D45Bld", 7);
        Guitar guitar = guitarRepository.save(savedGuitar);

        assertThat(guitar.getModel()).isEqualTo(savedGuitar.getModel());
        assertThat(guitar.getBrand()).isEqualTo(savedGuitar.getBrand());
        assertThat(guitar.getStrings()).isEqualTo(savedGuitar.getStrings());
    }


}