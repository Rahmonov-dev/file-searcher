package org.architect.filereader.controller;

import lombok.RequiredArgsConstructor;
import org.architect.filereader.service.FileService;
import org.architect.filereader.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/search/{pattern}")
    public ResponseEntity<Result> searchFiles(@PathVariable String pattern ) {
        return ResponseEntity.ok(fileService.searchFiles(pattern));
    }
}
