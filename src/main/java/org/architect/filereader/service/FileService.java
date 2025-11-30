package org.architect.filereader.service;

import org.architect.filereader.dto.Response;
import org.architect.filereader.dto.Result;
import org.architect.filereader.exceptions.FileReadException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
          futures.add(executorService.submit(() -> task(searchText)));
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
    public List<Response> task (String searchText) {
        List<Response> responses = new ArrayList<>();
        searchText = searchText.toLowerCase();
        for (int i=0; i< files.size(); i++){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        "/home/baxti/IdeaProjects/file-reader/src/main/resources/files/"+ files.get(i)));
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (line.toLowerCase().contains(searchText)) {
                        Response response = new Response();
                        response.setFileName(files.get(i));
                        response.setLineText(line);
                        response.setLineNumber(String.valueOf(lineNumber));
                        responses.add(response);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new FileReadException("File not found "+ files.get(i));
            } catch (IOException e) {
                throw new FileReadException("Failed to read file "+ files.get(i));
            }
        }
        return responses ;
    }
}
