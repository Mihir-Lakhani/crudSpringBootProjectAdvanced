package in.mihirlakhani.crudSpringBootProject.controller;

import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.service.StudentServiceImpl;
import in.mihirlakhani.crudSpringBootProject.service.StudentServiceLogging;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private StudentServiceLogging studentServiceLogging;

    public StudentController(StudentServiceLogging studentServiceLogging) {
        this.studentServiceLogging = studentServiceLogging;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDTO> createStudent(@Valid @RequestBody CreateStudentRequestDTO createStudentRequestDTO){

        System.out.println("Inside " + this.getClass().getSimpleName());

        CreateStudentResponseDTO createdStudent = studentServiceLogging.createStudent(createStudentRequestDTO);

        if (createdStudent == null){
            return ResponseEntity
                    .status(400)
                    .build();
        }

        return ResponseEntity
                .status(201)
                .body(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<CreateStudentResponseDTO> getStudent(@PathVariable Long id){
        CreateStudentResponseDTO studentResp = studentServiceLogging.getStudent(id);


        return ResponseEntity
                .status(200)
                .body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> getAllStudents(){
        List<CreateStudentResponseDTO> studentList= studentServiceLogging.getAllStudent();

        if (studentList.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .build();
        }

        return ResponseEntity
                .status(200)
                .body(studentList);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateStudentResponseDTO> updateStudent(@PathVariable Long id,
                                                                  @RequestBody UpdateStudentRequestDTO updateStudentRequestDTO){
        UpdateStudentResponseDTO updateStudentResponseDTO = studentServiceLogging.updateStudent(id, updateStudentRequestDTO);

        if (updateStudentResponseDTO == null){
            return ResponseEntity
                    .status(404)
                    .build();
        }

        return ResponseEntity
                .status(200)
                .body(updateStudentResponseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){

        Boolean isDeleted = studentServiceLogging.deleteStudent(id);

        if (!isDeleted){
            return ResponseEntity
                    .status(404)
                    .body("User does Not exist");
        }

        return ResponseEntity
                .status(200)
                .body("User deleted successfully");
    }
}
