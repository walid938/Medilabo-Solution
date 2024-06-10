package msnote.service;

import msnote.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> getNotesByPatientId(int patientId);

    Note getNoteById(String id);

    Note addNoteToPatient(int patientId, Note note);

    Note updateNoteById(String id, Note note);

    void deleteNoteById(String id);

    void deleteNoteByPatientId(int patientId);
}
