package filereader.pojo;

/**
 * Class for containing the details of a file
 **/
public class FileDetails {

    private String name;
    private String mimeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleInfo;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleInfo = vehicleDetails;
    }

    private Long fileSize;
    private String fileExtension;
    private VehicleDetails vehicleInfo;

}
