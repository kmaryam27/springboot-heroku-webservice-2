package com.galvanize.simplegitarapi.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GuitarTest {//1
    @Test
    public void isGuitarInstance(){
        Guitar guitar = new Guitar("Guild", "D45Bld", 7);
        assertThat(guitar.getModel()).isEqualTo("Guild");
        assertThat(guitar.getBrand()).isEqualTo("D45Bld");
        assertThat(guitar.getStrings()).isEqualTo(7);
        assertThat(guitar.getId()).isEqualTo(null);

    }
}
