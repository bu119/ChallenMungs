package com.ssafy.ChallenMungs.common.util;

import com.ssafy.ChallenMungs.challenge.common.controller.ChallengeController;
import com.ssafy.ChallenMungs.challenge.common.entity.Challenge;
import com.ssafy.ChallenMungs.challenge.common.service.ChallengeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileManager {
    @Value("${result.windows}")
    String windowsPath;

    @Value("${result.ubuntu}")
    String ubuntuPath;

    String directoryPath;

    private Logger log = LoggerFactory.getLogger(FileManager.class);

    @Autowired
    ChallengeService challengeService;

    @PostConstruct
    void init() {
        List<Long> challenges = challengeService.findAllByStatus(2);
        if (System.getProperty("os.name").substring(0, 3).equals("Win")) directoryPath = windowsPath;
        else directoryPath = ubuntuPath;
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && !challenges.contains(file.getName())) {
                file.delete();
            }
        }
    }

    public void saveResult(String title, String content) {
        File file = new File(directoryPath + File.separator + title);

        try {
            log.info("파일을 생성해요!");
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadResult(String title) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(directoryPath + File.separator + title)));
            String line = null;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }

        return stringBuilder.toString();
    }
}
