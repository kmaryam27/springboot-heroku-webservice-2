package com.galvanize.simplegitarapi.controllers;

import com.galvanize.simplegitarapi.entity.Guitar;
import com.galvanize.simplegitarapi.entity.GuitarList;
import com.galvanize.simplegitarapi.entity.User;
import com.galvanize.simplegitarapi.exceptions.GuitarNotFoundException;
import com.galvanize.simplegitarapi.services.GuitarService;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guitars")
public class GuitarController {//5

    @Autowired
    private GuitarService guitarService;

    /**
     * find a Gitar by uniqu model
     * @param model
     * @return
     */
    @GetMapping("/model/{model}")
    private Guitar getGuitarByModel(@PathVariable String model){
        return guitarService.getSelectedGuitarByModel(model);
    }

    /**
     * find a Gitar by uniqu Id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    private Guitar getGuitarById(@PathVariable Long id){
        return guitarService.getSelectedGuitarById(id);
    }

//    /**
//     * get all Gitars
//     * @return
//     */
//    @GetMapping
//    private List<Guitar> getAllGuitars(){
//        return guitarService.getAllGuitarGitarsDetails();
//    }

    /**
     * get all Gitars
     * @return
     */
    @GetMapping
    private GuitarList getAllGuitars(){
        GuitarList guitarList = new GuitarList();
        guitarList.setGuitarList(guitarService.getAllGuitarGitarsDetails());
         return guitarList;
    }

    /* because for test we have to use variable of service only autowiring does not work*/
    public GuitarController(GuitarService guitarService) {
        this.guitarService = guitarService;
    }

    /**
     * Add new Guitar
     * @param guitar
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guitar createGuitar(@RequestBody @Valid Guitar guitar) {
            return guitarService.addNewGuitarInstance(guitar);
    }

    /**
     * Update a guitar entity
     * @param id
     * @param guitar
     * @return
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Guitar updateGuitar(@PathVariable Long id, @RequestBody @Valid Guitar guitar) {
        return guitarService.updateSelectedGuitarById(id, guitar);
    }


    /**
     * Exception Handler
     * @param e
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void GuitarNotFoundHandler(GuitarNotFoundException e){}


    /*******************************************WEB SERVICE************************************/
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  WebClient.Builder webClientBuilder;
    @RequestMapping("/webservices/{id}")
    public List<User> getGuitarsfromOtherWebServices(@PathVariable("id") Long id){
//        return Collections.singletonList( new Guitar("test","tt",7));
        /*******************/
//        get all guitars ids
//        for each guitar id call another service for guitar info or ...
//        put them togather
//        ****** we need Guitar model inside of this service because of Guitar
//        List<Guitar> guitarList = Arrays.asList(
//                new Guitar("test1","tt",5),
//                new Guitar("test2","tt",7)
//        );
//
//        return guitarList.stream().map(g -> new User("mary"))
//                .collect(Collectors.toList());
        /*******************/
////        RestTemplate restTemplate = new RestTemplate();
//
//                List<Guitar> guitarList = Arrays.asList(
//                new Guitar("test1","tt",5),
//                new Guitar("test2","tt",8)
//        );
//        return guitarList.stream().map(g -> {
//            Guitar myGuitar = restTemplate.getForObject("https://springboot-guitar-api.herokuapp.com/guitars/8",Guitar.class);
//            return new User("mary", myGuitar.getModel());
//        })
//                .collect(Collectors.toList());

        /*******************Asynchronous style*/
//        WebClient.Builder builder = WebClient.builder();//like new restTemplate so can use bean and Autowired
        /*******************Asynchronous style*/
//        List<Guitar> guitarList = Arrays.asList(
//                new Guitar("test1","tt",5),
//                new Guitar("test2","tt",8)
//        );
//        return guitarList.stream().map(g -> {
////            Guitar myGuitar = restTemplate.getForObject("https://springboot-guitar-api.herokuapp.com/guitars/8",Guitar.class);
//            Guitar myGuitar = webClientBuilder.build()
//                    .get()
//                    .uri("https://springboot-guitar-api.herokuapp.com/guitars/8")
//                    .retrieve()
//                    .bodyToMono(Guitar.class)
//                    .block();
//            return new User("mary", myGuitar.getModel());
//        })
//                .collect(Collectors.toList());

        /*******************/
//                List<Guitar> guitarList = restTemplate.getForObject("https://springboot-guitar-api.herokuapp.com/guitars",
//                        ParameterizedType<List<Guitar>>);
       GuitarList guitarList = restTemplate.getForObject("https://springboot-guitar-api.herokuapp.com/guitars", GuitarList.class);
       /*IMPORTANT:* In  https://springboot-guitar-api.herokuapp.com/guitars should return GuitarList not List<Guitars>**LINE 62**/
        return guitarList.getGuitarList().stream().map(g -> {
//            Guitar myGuitar = restTemplate.getForObject("https://springboot-guitar-api.herokuapp.com/guitars/8",Guitar.class);
            Guitar myGuitar = restTemplate.getForObject("https://simple-gitar-api/guitars/8", Guitar.class);
            return new User("mary", myGuitar.getModel());
        })
                .collect(Collectors.toList());
    }
}
