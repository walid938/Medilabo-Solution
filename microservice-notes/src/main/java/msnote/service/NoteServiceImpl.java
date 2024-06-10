package msnote.service;


import msnote.model.Note;
import msnote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getNotesByPatientId(int patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    @Override
    public Note getNoteById(String id) {
        return noteRepository.findById(id).get();
    }

    @Override
    public Note addNoteToPatient(int patientId, Note note) {
        note.setPatientId(patientId);
        return noteRepository.save(note);
    }

    @Override
    public Note updateNoteById(String id, Note note) {
        Note existingNote = noteRepository.findById(id).orElse(null);
        if (existingNote != null) {
            existingNote.setNoteContent(note.getNoteContent());
            // Ajoutez d'autres champs si nécessaire
            return noteRepository.save(existingNote);
        }
        return null;
    }

    @Override
    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void deleteNoteByPatientId(int patientId) {
        List<Note> allPatientsNotes = noteRepository.findByPatientId(patientId);
        noteRepository.deleteAll(allPatientsNotes);
    }
}