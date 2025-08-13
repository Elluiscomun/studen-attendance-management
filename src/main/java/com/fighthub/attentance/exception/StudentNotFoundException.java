package com.fighthub.attentance.exception;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException() {
        super("Could not find student ");
    }

}
