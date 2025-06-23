package com.medilabo.frontapplication.constant;

public interface ErrorConstant {
    String ERROR_ATTRIBUTE = "error";

    String ERROR_NOTE_NOT_FOUND       = "Note could not be found";
    String ERROR_NOTE_CREATION        = "Note could not be created";
    String ERROR_NOTE_MODIFICATION    = "Note could not be updated";
    String ERROR_NOTE_DELETION        = "Note could not be deleted";

    String ERROR_PATIENT_NOT_FOUND    = "Patient could not be found";
    String ERROR_PATIENT_CREATION     = "Patient could not be created";
    String ERROR_PATIENT_MODIFICATION = "Patient could not be updated";
    String ERROR_PATIENT_DELETION     = "Patient could not be deleted";

    String ERROR_RISK                 = "Risk could not be evaluated at that time";
}
