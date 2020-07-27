package org.meteorite.com.service;

import org.meteorite.com.dto.FileDTO;

import java.io.File;
import java.util.List;

public interface FileService {
    FileDTO upLoad(File newFile);

    List<String> getObjectList();

}
