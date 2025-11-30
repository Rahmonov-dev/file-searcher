package org.architect.filereader.dto;

import lombok.Data;

@Data
public class Response {
    private String fileName;
    private String lineText;
    private String lineNumber;
}
