package filereaderservice;

import filereader.pojo.FileDetails;
import filereader.pojo.VehicleDetails;
import filereader.service.FileReaderService;
import filereader.service.FileReaderServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * unit tests for the service using Junit framework
 */

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;
    private static String DIRECTORY = System.getProperty("user.dir") + "/testfiles";
    private static String EMPTY_DIRECTORY = System.getProperty("user.dir") + "/testfiles2";

    @Before
    public void setUp() throws Exception {

        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void testFileCount() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
        assertEquals(10, fileDetailsList.size());
    }

    @Test
    public void testEmptyDirectory() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(EMPTY_DIRECTORY);
        assertEquals(0, fileDetailsList.size());
    }

    @Test
    public void testVehicleDetails() throws Exception {
        List<FileDetails> fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
        VehicleDetails vehicleDetails = fileReaderService.getVehicleDetailsFromFile("file6.csv");
        assertEquals("GJ13EBX", vehicleDetails.getRegNo());
        assertEquals("CITROEN", vehicleDetails.getMake());
        assertEquals("WHITE", vehicleDetails.getColour());
    }

}
