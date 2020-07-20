package org.meteorite.com.service;

import org.meteorite.com.dto.FileDTO;

import java.io.File;

public interface FileService {
    FileDTO upLoad(File newFile);
}
