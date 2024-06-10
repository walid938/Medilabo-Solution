package msnote.controller;

import msnote.model.Note;
import msnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable int patientId) {
        List<Note> notes = noteService.getNotesByPatientId(patientId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<Note> addNoteToPatient(@PathVariable int patientId, @RequestBody Note note) {
        note.setPatientId(patientId);
        Note createdNote = noteService.addNoteToPatient(patientId, note);
        return ResponseEntity.ok(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNoteById(@PathVariable String id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNoteById(id, note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteNoteByPatient(@PathVariable int patientId){
        noteService.deleteNoteByPatientId(patientId);
        return ResponseEntity.noContent().build();
    }

}
