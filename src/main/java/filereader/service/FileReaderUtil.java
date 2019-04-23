package filereader.service;

import filereader.pojo.FileDetails;
import filereader.pojo.VehicleDetails;

import javax.activation.MimeType;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Scanner;

public class FileReaderUtil {
    public static FileDetails setFileDetails(File file) throws IOException {

        FileDetails fileDetails = new FileDetails();
        fileDetails.setName(file.getName());
        fileDetails.setFileSize(file.length());

        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(String.valueOf(file.toPath()));

        fileDetails.setMimeType(type);

        String extension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i+1);
        }
        fileDetails.setFileExtension(extension);

        VehicleDetails vd = setVehicleDetals(file);
        if(vd == null ) {
            return null;
        }
        fileDetails.setVehicleDetails(vd);
        return fileDetails;
    }

    private static VehicleDetails setVehicleDetals(File file) throws IOException{
        Scanner scanner = new Scanner(file);
        String line = "";
        String[] info;
        if(scanner.hasNext()) {
            line = scanner.nextLine();
        }
        scanner.close();
        if(line.contains(",")) {
            info = line.split(",");
        } else {
            info = line.split("\\s+");
        }
        VehicleDetails vehicleDetails = new VehicleDetails();
        if(info.length != 3) {
            return null;
        }
        vehicleDetails.setRegNo(info[0].toUpperCase().trim());
        vehicleDetails.setMake(info[1].toUpperCase().trim());
        vehicleDetails.setColour(info[2].toUpperCase().trim());

        return vehicleDetails;
    }
}
