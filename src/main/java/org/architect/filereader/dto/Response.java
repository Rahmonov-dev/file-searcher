package org.architect.filereader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private String fileName;
    private String lineText;
    private String lineNumber;
}
