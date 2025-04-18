package com.medilabo.frontapplication.context;

import com.medilabo.frontapplication.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SessionContext {
    private String credential ="";
    private User authanticatedUser;
    private String message="";
    private String url="";
    private int authenticationErrorCode=0;

}
