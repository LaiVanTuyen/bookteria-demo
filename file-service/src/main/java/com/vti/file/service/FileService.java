package com.vti.file.service;

import com.vti.file.dto.response.FileResponse;
import com.vti.file.mapper.FileMgmtMapper;
import com.vti.file.repository.FileMgmtRepository;
import com.vti.file.repository.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileService {
    FileRepository fileRepository;
    FileMgmtRepository fileMgmtRepository;
    FileMgmtMapper fileMgmtMapper;

    public FileResponse uploadFile(MultipartFile file) throws IOException {
        // Store the file in the local file system
        var fileInfo = fileRepository.store(file);
        // Create file management information
        var fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        fileMgmt.setOwnerId(userId);
        fileMgmtRepository.save(fileMgmt);

        return FileResponse.builder()
                .originalFileName(file.getOriginalFilename())
                .url(fileInfo.getPath())
                .build();
    }
}
