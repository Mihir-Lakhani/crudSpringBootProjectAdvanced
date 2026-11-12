package in.mihirlakhani.crudSpringBootProject.service;

import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceLogging implements StudentService{

    private StudentServiceImpl studentServiceImpl;

    public StudentServiceLogging(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @Override
    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDTO) {
        return studentServiceImpl.createStudent(createStudentRequestDTO);
    }

    @Override
    public CreateStudentResponseDTO getStudent(Long id) {
        return studentServiceImpl.getStudent(id);
    }

    @Override
    public List<CreateStudentResponseDTO> getAllStudent() {
        return studentServiceImpl.getAllStudent();
    }

    @Override
    public UpdateStudentResponseDTO updateStudent(Long id, UpdateStudentRequestDTO updateStudentRequestDTO) {
        return studentServiceImpl.updateStudent(id, updateStudentRequestDTO);
    }

    @Override
    public Boolean deleteStudent(Long id) {
        return studentServiceImpl.deleteStudent(id);
    }
}