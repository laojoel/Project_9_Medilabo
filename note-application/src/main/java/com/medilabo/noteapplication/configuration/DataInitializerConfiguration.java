package com.medilabo.noteapplication.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import com.medilabo.noteapplication.model.Note;
import com.medilabo.noteapplication.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataInitializerConfiguration {

    /*
     * DATABASE INITIALIZATION IS AUTOMATICALLY TRIGGERED TO POPULATE THE DATABASE AT FIRST
     * Similar to data.sql and schema.sql do for mySQL
     */

    private final NoteRepository noteRepository;
    public DataInitializerConfiguration(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if (noteRepository.count() == 0) {
                log.info("mangoDB (noSQL) Database initialization with bases given values");
                List<Note> notes = new ArrayList<>();
                add(notes, 1, "TestNone", "Le patient déclare qu'il 'se sent très bien' masse égal ou inférieur à la masse recommandé");
                add(notes, 2, "TestBorderline", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
                add(notes, 2, "TestBorderline", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
                add(notes, 3, "TestInDanger", "Le patient déclare qu'il fume depuis peu");
                add(notes, 3, "TestInDanger", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
                add(notes, 4, "TestEarlyOnset", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
                add(notes, 4, "TestEarlyOnset", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
                add(notes, 4, "TestEarlyOnset", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");
                add(notes, 4, "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");
                noteRepository.saveAll(notes);
            }
        };
    }

    private void add(List<Note> notes, int patId, String patient, String content) {
        Note note = new Note();
        note.setPatId(patId);
        note.setPatient(patient);
        note.setNote(content);
        notes.add(note);
    }
}
