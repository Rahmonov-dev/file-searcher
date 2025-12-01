package org.architect.filereader.service;

import org.architect.filereader.dto.Response;
import org.architect.filereader.dto.Result;
import org.architect.filereader.exceptions.FileReadException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Service
public class FileService {
    private static  List<String> files= List.of(new String[]{"" +
            "file1.txt",
            "file2.txt",
            "file3.txt"});

    public Result searchFiles(String searchText) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<List<Response>>> futures = new ArrayList<>();
      for(String file : files){
          futures.add(executorService.submit(() -> task(file, searchText)));
      }
        List<Response> allResponses = new ArrayList<>();

        for(Future<List<Response>> future : futures){
            try {
                allResponses.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Task xatosi: " + e.getMessage());
            }
        }
        Result result = new Result();
        result.setResponses(allResponses);
        result.setCountOfResponses(allResponses.size());

        executorService.shutdown();
        return result;
    }
    public List<Response> task (String file,String searchText) {
        List<Response> responses = new ArrayList<>();
        Path path = Paths.get("/home/baxti/IdeaProjects/file-reader/src/main/resources/files/" +file);
        searchText = searchText.toLowerCase().trim();

            try(Stream<String> stream = Files.lines(path)) {
                final int[] lineNumber = {0};

                String finalSearchText = searchText;
                stream.forEach(line -> {
                    lineNumber[0]++;

                    if (line.toLowerCase().contains(finalSearchText)) {
                        responses.add(new Response(
                                file,
                                line,
                                String.valueOf(lineNumber[0])
                        ));
                    }
                });
            } catch (FileNotFoundException e) {
                throw new FileReadException("File not found "+ file);
            } catch (IOException e) {
                throw new FileReadException("Failed to read file "+ file);
            }
        return responses ;
    }
}
