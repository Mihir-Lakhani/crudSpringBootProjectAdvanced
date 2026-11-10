package in.mihirlakhani.crudSpringBootProject.controller;

import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.entity.Student;
import in.mihirlakhani.crudSpringBootProject.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDTO> createStudent(@RequestBody CreateStudentRequestDTO createStudentRequestDTO){

        System.out.println("Inside " + this.getClass().getSimpleName());

        CreateStudentResponseDTO createdStudent = studentService.createStudent(createStudentRequestDTO);

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
        Optional<CreateStudentResponseDTO> studentResp = studentService.getStudent(id);

        if(!studentResp.isPresent()){
            return ResponseEntity
                    .status(404)
                    .build();
        }

        return ResponseEntity
                .status(200)
                .body(studentResp.get());
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> getAllStudents(){
        List<CreateStudentResponseDTO> studentList= studentService.getAllStudent();

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
        UpdateStudentResponseDTO updateStudentResponseDTO = studentService.updateStudent(id, updateStudentRequestDTO);

        if (updateStudentResponseDTO == null){
            return ResponseEntity
                    .status(404)
                    .build();
        }

        return ResponseEntity
                .status(200)
                .body(updateStudentResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){

        Boolean isDeleted = studentService.deleteStudent(id);

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