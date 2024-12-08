package sgsits.cse.dis.administration.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sgsits.cse.dis.administration.constants.RestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sgsits.cse.dis.administration.dto.response.ResponseMessage;
import sgsits.cse.dis.administration.models.Leave;
import sgsits.cse.dis.administration.dto.request.LeaveRequestForm;
import sgsits.cse.dis.administration.dto.response.LeaveApplicationResponse;
//import sgsits.cse.dis.administration.service.fileStorage.FileStorageService;
import sgsits.cse.dis.administration.service.leave.LeaveApplicationService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/home")
public class LeaveApplicationController {
    @Autowired
    LeaveApplicationService leaveApplicationService;

//    @Autowired
//    FileStorageService fileStorageService;

    @GetMapping(value = RestAPI.GET_LEAVE_WHICH_ARE_PENDING_TO_BE_ASSIGNED)
    public ResponseEntity<List<Leave>>  getLeaveWhichArePendingToBeAssigned(){
        if(Optional.ofNullable(leaveApplicationService.getLeaveWhichArePendingToBeAssigned()).isPresent()){
            return new ResponseEntity(leaveApplicationService.getLeaveWhichArePendingToBeAssigned(),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("No Leaves Pending To Be Assigned"),HttpStatus.OK);
    }

//    @PostMapping(value = RestAPI.POST_LEAVE_SUPPORTING_DOCUMENT)
//    public ResponseEntity<?> postLeaveSupportingDocument(@RequestParam("id") String leaveId,@RequestPart("file") MultipartFile file){
//        leaveApplicationService.postLeaveSupportingDocument(leaveId,file);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @PostMapping(value= RestAPI.POST_LEAVE_APPLICATION)
    public ResponseEntity<?> postLeaveApplication(@RequestBody LeaveRequestForm leaveRequestForm)
    {
        LeaveApplicationResponse response = leaveApplicationService.createLeaveApplcation(leaveRequestForm);
        return new ResponseEntity<LeaveApplicationResponse>(response,HttpStatus.OK);
    }
    @GetMapping(value = RestAPI.GET_LEAVE_OF_A_STUDENT)
    public ResponseEntity<?> getLeavesByStudentId(@RequestParam("studentId") String studentId){
        if(Optional.ofNullable(leaveApplicationService.getAllLeavesOfAStudent(studentId)).isPresent()){
            return new ResponseEntity(leaveApplicationService.getAllLeavesOfAStudent(studentId),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("No Leaves Existing"),HttpStatus.OK);
    }
    @DeleteMapping(value = RestAPI.DELETE_LEAVE_BY_LEAVE_ID)
    public ResponseEntity<String> deleteLeaveByLeaveId(@RequestParam("leaveId") String leaveId){
        if(leaveApplicationService.deleteLeaveByLeaveId(leaveId).equals("Deleted Successfully")){
            return new ResponseEntity(new ResponseMessage("Deleted Successfully"),HttpStatus.OK);
        }
        else return ResponseEntity.badRequest().body("Error Deleting");
    }
    @PutMapping(value = RestAPI.PUT_ASSIGNED_TO_BY_LEAVE_ID)
    public ResponseEntity<String> putAssignedToByLeaveId(@RequestParam("id")String id,@RequestParam("assignedTo")String assignedTo){
        return new ResponseEntity(new ResponseMessage(leaveApplicationService.putAssignedToByLeaveId(id,assignedTo)), HttpStatus.OK);
    }
    @PutMapping(value = RestAPI.PUT_LEAVE_STATUS_BY_LEAVE_ID)
    public ResponseEntity<String> putLeaveStatusByLeaveId(@RequestParam("leaveId")String leaveId,@RequestParam("status")String status){
        return new ResponseEntity(new ResponseMessage(leaveApplicationService.putLeaveStatusByLeaveId(status,leaveId)),HttpStatus.OK);
    }
    @GetMapping(value = RestAPI.GET_LEAVE_BY_ASSIGNED_ID)
    public ResponseEntity<?> getLeaveByAssignedId(@RequestParam("assignedId")String assignedId){
        if(Optional.ofNullable(leaveApplicationService.getLeavesByAssignedId(assignedId)).isPresent()){
            return new ResponseEntity(leaveApplicationService.getLeavesByAssignedId(assignedId),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("No Leaves Existing"),HttpStatus.OK);
    }

    @GetMapping(value = RestAPI.GET_LAST_FIVE_DAY_LEAVE)
    public ResponseEntity<?> getLastFiveDayLeave(){
        if(Optional.ofNullable(leaveApplicationService.getLeaveByCreatedDate()).isPresent()){
            return new ResponseEntity(leaveApplicationService.getLeaveByCreatedDate(),HttpStatus.OK);
        }
        else return new ResponseEntity(new ResponseMessage("No Leaves Existing"),HttpStatus.OK);
    }


//    @GetMapping(value = RestAPI.GET_LEAVE_SUPPORTING_DOCUMENTS)
//    public ResponseEntity<?> getLeaveSupportingDocumentById(@RequestParam("leaveId")String leaveId){
////        if(!leaveApplicationService.getLeavesByAssignedId(leaveId).isEmpty()){
//        return fileStorageService.load(leaveId);
////        }
////        else return ResponseEntity.ok(null);
//    }
}
