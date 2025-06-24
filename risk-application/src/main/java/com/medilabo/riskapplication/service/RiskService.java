package com.medilabo.riskapplication.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.medilabo.riskapplication.enumeration.TriggerEnumeration;
import com.medilabo.riskapplication.model.Note;
import com.medilabo.riskapplication.model.Patient;
import lombok.RequiredArgsConstructor;

import static com.medilabo.riskapplication.enumeration.RiskLevelEnumeration.*;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final NoteService noteService;
    private final PatientService patientService;

    public String riskEvaluation(long patId) {
        // Retrieve information from sub-applications
        Patient patient = patientService.getPatientId(patId);
        List<Note> notes = noteService.getNotesPatId(patId);

        // verify integrity of the required information
        if (patient == null || notes == null) {return null;}
        int age = patient.getAge();
        if (age == -1) {return null;}
        char gender = patient.getGenderChar();
        if (gender != 'M' && gender != 'F') {return null;}

        // read notes and count triggers
        int triggerCount = triggerCount(notes);

        // Apply logics to define the Risk level
        if      (gender == 'M' && age < 30 && triggerCount == 3)        {return IN_DANGER;}
        else if (gender == 'F' && age < 30 && triggerCount == 4)        {return IN_DANGER;}
        else if (age >= 30 && (triggerCount == 6 || triggerCount == 7)) {return IN_DANGER;}

        else if (age >= 30 && triggerCount >= 2 && triggerCount <= 5)   {return BORDERLINE;}

        else if (gender == 'M' && age < 30 && triggerCount >= 5)        {return EARLY_ONSET;}
        else if (gender == 'F' && age < 30 && triggerCount >= 7)        {return EARLY_ONSET;}
        else if (age >= 30 && (triggerCount >= 7))                      {return EARLY_ONSET;}

        else if (triggerCount == 0)                                     {return NONE;}

        else                                                            {return UNDEFINED;}
    }

    public int triggerCount(List<Note> notes) {
        int count = 0;
        for (Note note : notes) {
            for (TriggerEnumeration trigger : TriggerEnumeration.values()) {
                // set both notes and triggers to lower cases to avoid any miss-matches
                if (note.getContent().toLowerCase().contains(trigger.getValue().toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
    }
}
