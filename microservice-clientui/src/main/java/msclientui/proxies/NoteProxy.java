package msclientui.proxies;

import msclientui.beans.NoteBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-gateway", url = "microservice-gateway:8181")
public interface NoteProxy {

    @GetMapping("/api/notes/patient/{patientId}")
    List<NoteBeans> getNotesByPatientId(@PathVariable int patientId);

    @GetMapping("/api/notes/note/{id}")
    NoteBeans getNoteById(@PathVariable("id") String id);

    @PostMapping("/api/notes/patient/{patientId}")
    NoteBeans addNoteToPatient(@PathVariable int patientId, @RequestBody NoteBeans noteBeans);

    @PutMapping("/api/notes/{id}")
    NoteBeans updateNoteById(@PathVariable String id, @RequestBody NoteBeans noteBeans);

    @DeleteMapping("/api/notes/{id}")
    void deleteNoteById(@PathVariable String id);

    @DeleteMapping("/api/notes/patient/{patientId}")
    void deleteNoteByPatientId(@PathVariable int patientId);


}