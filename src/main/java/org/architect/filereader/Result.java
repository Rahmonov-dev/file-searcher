package org.architect.filereader;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    private Integer countOfResponses;
    private List<Response> responses;
}
