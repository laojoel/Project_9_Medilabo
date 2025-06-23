package com.medilabo.riskapplication.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Risk {
    private int level;
    private String name;

    public Risk(int level, String name) {
        this.level = level;
        this.name = name;
    }
}
