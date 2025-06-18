package com.medilabo.riskapplication.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.medilabo.riskapplication.enumeration.TriggerEnumeration;
import com.medilabo.riskapplication.model.Note;
import com.medilabo.riskapplication.model.Patient;

import static com.medilabo.riskapplication.enumeration.RiskLevelEnumeration.*;

@Service
public class RiskService {

    public String estimateRisk(Patient patient, List<Note> notes) {
        int triggerCount = triggerCount(notes);

        // verify integrity
        int age = patient.getAge();
        if (age == -1) {return null;}
        char gender = patient.getGenderChar();
        if (gender == 'x') {return null;}

        if (triggerCount == 0) {
            return NONE;
        }
        else if (age >= 30 && triggerCount >= 2 && triggerCount <= 5) {
            return BORDERLINE;
        }
        else if (gender == 'M' && age < 30 && triggerCount >= 3) {
            return IN_DANGER;
        }
        else if (gender == 'F' && age < 30 && triggerCount >= 4) {
            return IN_DANGER;
        }
        else if (age >= 30 && (triggerCount == 6 || triggerCount == 7)) {
            return IN_DANGER;
        }
        else if (gender == 'M' && age < 30 && triggerCount >= 5) { // Impossible, but required on the demand.
            return EARLY_ONSET;
        }
        else if (gender == 'F' && age < 30 && triggerCount >= 7) { // Impossible, but required on the demand.
            return EARLY_ONSET;
        }
        else if (age >= 30 && triggerCount >= 8) {
            return IN_DANGER;
        }
        else {
            return UNDEFINED;
        }
    }


    public int triggerCount(List<Note> notes) {
        int count = 0;
        for (Note note : notes) {
            for (TriggerEnumeration trigger : TriggerEnumeration.values()) {
                if (note.getContent().toLowerCase().contains(trigger.getValue())) {
                    count++;
                }
            }
        }
        return count;
    }
}
