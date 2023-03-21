package com.ssafy.ChallenMungs.image.service;

import com.ssafy.ChallenMungs.image.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String saveFile(MultipartFile file,String folderName) throws IOException;
    public void deleteFile(FileDto fileDto);

}
