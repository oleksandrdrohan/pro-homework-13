package academy.prog.chatserversprint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FileDTO {

    private Long id;

    private String fileName;

    private String fileData;

    private byte[] fileDataByte;
}
