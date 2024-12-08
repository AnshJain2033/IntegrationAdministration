package sgsits.cse.dis.administration.dto.request;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class LeaveRequestForm {
    String subject;
    String description;
    String studentId;
    //    List<File> uploadedFiles;
    String startDate;
    String endDate;
//    MultipartFile file;
}
