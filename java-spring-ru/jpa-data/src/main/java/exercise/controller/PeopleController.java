package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable Long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Person> people() {
        return personRepository.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
    // END
}
