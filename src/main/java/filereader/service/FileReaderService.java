package filereader.service;

/**
 * Interface to read files in a directory and return information from
 * the files
 */
import filereader.pojo.FileDetails;
import filereader.pojo.VehicleDetails;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileReaderService {

    public List<FileDetails> getFileContents(String path) throws IOException;

    public FileDetails getFileDetailsFromFile(String filename);

    public VehicleDetails getVehicleDetailsFromFile(String filename);

}