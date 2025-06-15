package com.medilabo.frontapplication.proxy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class NoteRoutes {
    @Value("${notes-uri}")
    private String allNotesUri;

    @Value("${notes-view-uri}")
    private String noteViewUri;

    @Value("${notes-create-uri}")
    private String noteCreateUri;

    @Value("${notes-update-uri}")
    private String noteUpdateUri;

    @Value("${notes-delete-uri}")
    private String noteDeleteUri;
}
