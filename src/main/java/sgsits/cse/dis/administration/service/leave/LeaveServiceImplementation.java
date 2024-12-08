package sgsits.cse.dis.administration.service.leave;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sgsits.cse.dis.administration.models.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgsits.cse.dis.administration.repo.LeaveRepository;
import sgsits.cse.dis.administration.repo.StudentRepository;
import sgsits.cse.dis.administration.dto.request.LeaveRequestForm;
import sgsits.cse.dis.administration.dto.response.LeaveApplicationResponse;
//import sgsits.cse.dis.administration.service.fileStorage.FileStorageService;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service("leaveService")
public class LeaveServiceImplementation implements LeaveApplicationService {
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    StudentRepository studentRepository;
//    @Autowired
//    FileStorageService fileStorageService;


    @Override
    public Optional<List<Leave>> getLeaveWhichArePendingToBeAssigned(){
        return leaveRepository.findLeaveWhichArePendingToBeAssigned();
    }
    @Override
    public String getDescriptionByLeaveId(String leaveId) {
        String description = leaveRepository.findById(leaveId).get().getDescription();
        return description;
    }

    @Override
    public Leave getLeaveDetails(String leaveId) {
        Leave leave = leaveRepository.findById(leaveId).get();
        return leave;
    }

    @Override
    public String checkLeaveStatus(String leaveId) {
        String checkStatus = leaveRepository.findById(leaveId).get().getStatus();
        return checkStatus;
    }

    @Override
    public Date getLeaveStartDate(String leaveId) {
        Date startDate = leaveRepository.findById(leaveId).get().getStartDate();
        return startDate;
    }

    @Override
    public Date getLeaveEndDate(String leaveId) {
        Date endDate = leaveRepository.findById(leaveId).get().getEndDate();
        return endDate;
    }

    @Override
    public LeaveApplicationResponse createLeaveApplcation(LeaveRequestForm leaveApplication) {
        Leave leave = new Leave();

        if (leaveApplication.getEndDate().compareTo(leaveApplication.getStartDate()) > 0) {
            String description = leaveApplication.getDescription();
            String subject = leaveApplication.getSubject();
            String startDate = leaveApplication.getStartDate();
            String endDate = leaveApplication.getEndDate();
            String studentId = leaveApplication.getStudentId();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                leave.setStartDate(formatter.parse(startDate));
                leave.setEndDate(formatter.parse(endDate));
                leave.setCreatedDate(formatter.parse(LocalDate.now().toString()));
            } catch (Exception e) {
            }
            leave.setDescription(description);
            leave.setSubject(subject);
            leave.setStudentId(studentId);
            leave.setAssignedTo(null);
            leave.setStatus("PENDING");
            try {
                leave.setId(UUID.randomUUID().toString());
                leaveRepository.save(leave);

                LeaveApplicationResponse response = new LeaveApplicationResponse();
                response.setLeave(leave);
                response.setMessage("Applied For Leave Successfully");
                return response;
            } catch (Exception e) {

                LeaveApplicationResponse response = new LeaveApplicationResponse();
                response.setLeave(null);
                response.setMessage("Exception Occurred "+e);
                return response;
            }

        } else {
            LeaveApplicationResponse response = new LeaveApplicationResponse();
            response.setLeave(null);
            response.setMessage("Error Creating a Leave");

            return response;
        }
    }

    @Override
    public Optional<List<Leave>> getAllLeavesOfAStudent(String studentId) {
        Optional<List<Leave>> studentLeaves = leaveRepository.findLeaveByStudentId(studentId);
        if (!studentLeaves.get().isEmpty()) {
            return studentLeaves;
        }
        return null;
    }

    @Override
    public String deleteLeaveByLeaveId(String leaveId) {
        if (leaveRepository.findById(leaveId).isPresent()) {
            leaveRepository.deleteById(leaveId);
            return "Deleted Successfully";
        } else return "Error Deleting the Leave";
    }

    @Transactional
    public String putAssignedToByLeaveId(String id, String assignedTo) {
        leaveRepository.putAssignedToByStudentId(assignedTo, id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Leave leave = leaveRepository.findById(id).get();
            leave.setModifiedDate(formatter.parse(new Date().toString()));
            leaveRepository.save(leave);
        }catch (Exception e){}

        return "Assigned Successfully";
    }
    @Transactional
    public String putLeaveStatusByLeaveId (String status,String leaveId){
        leaveRepository.putLeaveStatusByLeaveId(status,leaveId);
        return "Status Updated Successfully";
    }
    @Override
    public Optional<List<Leave>> getLeavesByAssignedId(String assignedId){
        if(!leaveRepository.findLeaveByAssignedId(assignedId).get().isEmpty()){
            return leaveRepository.findLeaveByAssignedId(assignedId);
        }
        else return null;
    }
    @Override
    public Optional<List<Leave>> getLeaveByCreatedDate(){
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try{
            formatter.parse(LocalDate.now().toString());
          return Optional.of(leaveRepository.findLeaveWhichArePendingAndAssigned().get().stream()
                  .filter(leave ->
                          (ChronoUnit.DAYS.between(leave.getCreatedDate().toInstant(),currentDate.toInstant())<5))
                  .collect(Collectors.toList()));
        }catch(Exception e){}
        return null;
    }
//    public void postLeaveSupportingDocument(String leaveId,MultipartFile file){
//        Optional<Leave> leave = leaveRepository.findById(leaveId);
//
//        try{
//
//            fileStorageService.saveFile(leave.get().getId(),file);
//        }catch(Exception e){
//            System.out.println("Exception Occurred in file upload"+e.getMessage());
//        }
//
//
//    }

}
