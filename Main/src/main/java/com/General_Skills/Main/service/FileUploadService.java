package com.General_Skills.Main.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadService {
    public String uploadFile(MultipartFile file, String username, String category, String subCategory) throws IOException {


        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("__dd-MM-yyyy HH.mm.ss");
        LocalDateTime myObj = LocalDateTime.now();
        String timeStamp = myObj.format(myFormatObj).toString();
        String path = "J:\\project\\fileBase\\"+ username + "\\" + category+ "\\"  ;

        Path path1 = Paths.get(path);
        Files.createDirectories(path1);

        String fileName = subCategory + timeStamp + ".jpg";
        file.transferTo(new File(path + fileName));

        return file.getOriginalFilename();
    }


}
