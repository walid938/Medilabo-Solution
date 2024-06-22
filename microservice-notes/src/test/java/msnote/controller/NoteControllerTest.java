package msnote.controller;

import msnote.model.Note;
import msnote.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotesByPatientId() {
        // Given
        int patientId = 1;
        List<Note> notes = Arrays.asList(new Note("1", patientId, "John Doe", "Note content"));
        when(noteService.getNotesByPatientId(patientId)).thenReturn(notes);

        // When
        ResponseEntity<List<Note>> response = noteController.getNotesByPatientId(patientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notes.size(), response.getBody().size());
        assertEquals(notes.get(0).getId(), response.getBody().get(0).getId());
    }

    @Test
    public void testGetNoteById() {
        // Given
        String noteId = "1";
        Note note = new Note(noteId, 1, "John Doe", "Note content");
        when(noteService.getNoteById(noteId)).thenReturn(note);

        // When
        ResponseEntity<Note> response = noteController.getNoteById(noteId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(note.getId(), response.getBody().getId());
        assertEquals(note.getPatientId(), response.getBody().getPatientId());
        assertEquals(note.getPatientName(), response.getBody().getPatientName());
        assertEquals(note.getNoteContent(), response.getBody().getNoteContent());
    }

    @Test
    public void testAddNoteToPatient() {
        // Given
        int patientId = 1;
        Note newNote = new Note(null, patientId, "John Doe", "New note");
        Note savedNote = new Note("1", patientId, "John Doe", "New note");
        when(noteService.addNoteToPatient(eq(patientId), any(Note.class))).thenReturn(savedNote);

        // When
        ResponseEntity<Note> response = noteController.addNoteToPatient(patientId, newNote);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedNote.getId(), response.getBody().getId());
        assertEquals(savedNote.getPatientId(), response.getBody().getPatientId());
        assertEquals(savedNote.getPatientName(), response.getBody().getPatientName());
        assertEquals(savedNote.getNoteContent(), response.getBody().getNoteContent());
    }

    @Test
    public void testUpdateNoteById() {
        // Given
        String noteId = "1";
        Note updatedNote = new Note(noteId, 1, "John Doe", "Updated note");
        when(noteService.updateNoteById(eq(noteId), any(Note.class))).thenReturn(updatedNote);

        // When
        ResponseEntity<Note> response = noteController.updateNoteById(noteId, updatedNote);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedNote.getId(), response.getBody().getId());
        assertEquals(updatedNote.getPatientId(), response.getBody().getPatientId());
        assertEquals(updatedNote.getPatientName(), response.getBody().getPatientName());
        assertEquals(updatedNote.getNoteContent(), response.getBody().getNoteContent());
    }

    @Test
    public void testDeleteNoteById() {
        // Given
        String noteId = "1";

        // When
        ResponseEntity<Void> response = noteController.deleteNoteById(noteId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noteService, times(1)).deleteNoteById(noteId);
    }

    @Test
    public void testDeleteNoteByPatient() {
        // Given
        int patientId = 1;

        // When
        ResponseEntity<Void> response = noteController.deleteNoteByPatient(patientId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noteService, times(1)).deleteNoteByPatientId(patientId);
    }
}
