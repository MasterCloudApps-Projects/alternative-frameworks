package org.acme.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class StudentMessage {

    @NonNull
    private String messageUuid;

    @NonNull
    private Student student;

}