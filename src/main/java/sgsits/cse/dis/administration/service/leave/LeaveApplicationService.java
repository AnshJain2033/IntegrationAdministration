package sgsits.cse.dis.administration.service.leave;

import org.springframework.web.multipart.MultipartFile;
import sgsits.cse.dis.administration.models.Leave;
import sgsits.cse.dis.administration.dto.request.LeaveRequestForm;
import sgsits.cse.dis.administration.dto.response.LeaveApplicationResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface LeaveApplicationService {
    public String getDescriptionByLeaveId(String leaveId);
    public Leave getLeaveDetails(String leaveId);
    public String checkLeaveStatus(String leaveId);
    public Date getLeaveStartDate(String leaveId);
    public Date getLeaveEndDate(String leaveId);
    public LeaveApplicationResponse createLeaveApplcation(LeaveRequestForm leaveApplication);
    public Optional<List<Leave>> getAllLeavesOfAStudent(String studentId);
    public String deleteLeaveByLeaveId(String leaveId);
    public String putAssignedToByLeaveId(String id,String assignedTo);
    public String putLeaveStatusByLeaveId(String status,String leaveId);
    public Optional<List<Leave>> getLeavesByAssignedId(String assignedId);
//    public void postLeaveSupportingDocument(String leaveId,MultipartFile file);
    public Optional<List<Leave>> getLeaveWhichArePendingToBeAssigned();
    public Optional<List<Leave>> getLeaveByCreatedDate();
}
