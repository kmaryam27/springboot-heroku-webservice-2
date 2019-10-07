package com.galvanize.simplegitarapi.repositories;

import com.galvanize.simplegitarapi.entity.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {

    public Guitar findByModel(String model);

//    @Query("SELECT c FROM City c WHERE c.name LIKE CONCAT('%',:ending, '%') AND c.population < :num")
//    List<City> findByNameEndingWithAndPopulationLessThan(@Param("ending") String ending,
//                                                         @Param("num") Integer num);
}
