package in.mihirlakhani.crudSpringBootProject.service;

import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentResponseDTO;

import java.util.*;


public interface StudentService {

    CreateStudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDTO);
    CreateStudentResponseDTO getStudent(Long id);
    List<CreateStudentResponseDTO> getAllStudent();
    UpdateStudentResponseDTO updateStudent(Long id, UpdateStudentRequestDTO updateStudentRequestDTO);
    Boolean deleteStudent(Long id);

    }