package org.architect.filereader;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static  List<String> files= List.of(new String[]{"" +
            "file1.txt",
            "file2.txt",
            "file3.txt"});


    public Result searchFiles(String searchText) {
        Result result = new Result();
        int count = 0;
        for (int i=0; i< files.size(); i++){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        "/home/baxti/IdeaProjects/file-reader/src/main/resources/files/"+ files.get(i)));
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    if (line.toLowerCase().contains(searchText.toLowerCase())) {
                        Response response = new Response();
                        response.setFileName(files.get(i));
                        response.setLineText(line);
                        response.setLineNumber(String.valueOf(lineNumber));
                        if (result.getResponses() == null) {
                            result.setResponses(new ArrayList<>());
                        }
                        result.getResponses().add(response);
                        count++;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        result.setCountOfResponses(count);
        return result;
    }
}
