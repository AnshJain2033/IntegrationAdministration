package sgsits.cse.dis.administration.dto.response;

import lombok.Data;
import sgsits.cse.dis.administration.models.Leave;

@Data
public class LeaveApplicationResponse {
    public Leave leave;
    //    public String status;
    public String message;
}
