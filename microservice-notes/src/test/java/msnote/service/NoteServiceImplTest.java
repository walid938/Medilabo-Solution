package msnote.service;

import msnote.model.Note;
import msnote.repository.NoteRepository;
import msnote.service.NoteService;
import msnote.service.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotesByPatientId() {
        // Given
        int patientId = 1;
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("1", patientId, "John Doe", "Note 1"));
        notes.add(new Note("2", patientId, "Jane Smith", "Note 2"));
        when(noteRepository.findByPatientId(patientId)).thenReturn(notes);

        // When
        List<Note> result = noteService.getNotesByPatientId(patientId);

        // Then
        assertEquals(2, result.size());
        assertEquals("Note 1", result.get(0).getNoteContent());
        assertEquals("Note 2", result.get(1).getNoteContent());
    }

    @Test
    public void testGetNoteById() {
        // Given
        String noteId = "1";
        Note note = new Note(noteId, 1, "John Doe", "Note 1");
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        // When
        Note result = noteService.getNoteById(noteId);

        // Then
        assertEquals(noteId, result.getId());
        assertEquals("Note 1", result.getNoteContent());
    }

    @Test
    public void testAddNoteToPatient() {
        // Given
        int patientId = 1;
        Note noteToAdd = new Note("1", patientId, "John Doe", "New Note");
        when(noteRepository.save(any(Note.class))).thenReturn(noteToAdd);

        // When
        Note result = noteService.addNoteToPatient(patientId, noteToAdd);

        // Then
        assertEquals("1", result.getId());
        assertEquals(patientId, result.getPatientId());
        assertEquals("New Note", result.getNoteContent());
    }

    @Test
    public void testUpdateNoteById() {
        // Given
        String noteId = "1";
        Note existingNote = new Note(noteId, 1, "John Doe", "Existing Note");
        Note updatedNote = new Note(noteId, 1, "John Doe", "Updated Note");
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);

        // When
        Note result = noteService.updateNoteById(noteId, updatedNote);

        // Then
        assertEquals(noteId, result.getId());
        assertEquals("Updated Note", result.getNoteContent());
    }

    @Test
    public void testDeleteNoteById() {
        // Given
        String noteId = "1";

        // When
        noteService.deleteNoteById(noteId);

        // Then
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    public void testDeleteNoteByPatientId() {
        // Given
        int patientId = 1;
        List<Note> patientNotes = new ArrayList<>();
        patientNotes.add(new Note("1", patientId, "John Doe", "Note 1"));
        patientNotes.add(new Note("2", patientId, "Jane Smith", "Note 2"));
        when(noteRepository.findByPatientId(patientId)).thenReturn(patientNotes);

        // When
        noteService.deleteNoteByPatientId(patientId);

        // Then
        verify(noteRepository, times(1)).deleteAll(patientNotes);
    }
}
