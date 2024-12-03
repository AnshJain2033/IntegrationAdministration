package sgsits.cse.dis.administration.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name="student")
@AllArgsConstructor
@Entity
public class Student {
    @Id
    public String id;
    public String studentName;
    public String enrollmentNumber;
    public String facultyAssigned;
}
