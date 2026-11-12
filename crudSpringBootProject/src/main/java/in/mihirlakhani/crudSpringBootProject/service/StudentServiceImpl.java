package in.mihirlakhani.crudSpringBootProject.service;

import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.UpdateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.entity.Student;
import in.mihirlakhani.crudSpringBootProject.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO createStudentRequestDTO){
        //business logic
        System.out.println("Inside " + this.getClass().getSimpleName());

        Student studentReq = dtoToEntity(createStudentRequestDTO);

        studentReq.setCreatedAt(LocalDateTime.now());
        studentReq.setUpdatedAt(LocalDateTime.now());
        Student studentResp = studentRepository.save(studentReq);
        return entityToDto(studentResp);
    }

    private CreateStudentResponseDTO entityToDto(Student student){
        CreateStudentResponseDTO createStudentResponseDTO = new CreateStudentResponseDTO();

        createStudentResponseDTO.setAge(student.getAge());
        createStudentResponseDTO.setSubject(student.getSubject());
        createStudentResponseDTO.setRollNo(student.getRollNo());
        createStudentResponseDTO.setEmail(student.getEmail());
        createStudentResponseDTO.setName(student.getName());
        createStudentResponseDTO.setId(student.getId());
        createStudentResponseDTO.setCreatedAt(student.getCreatedAt());
        createStudentResponseDTO.setUpdatedAt(student.getUpdatedAt());

        return createStudentResponseDTO;
    }

    private Student dtoToEntity(CreateStudentRequestDTO createStudentRequestDTO){
        Student student = new Student();

        student.setAge(createStudentRequestDTO.getAge());
        student.setSubject(createStudentRequestDTO.getSubject());
        student.setRollNo(createStudentRequestDTO.getRollNo());
        student.setEmail(createStudentRequestDTO.getEmail());
        student.setName(createStudentRequestDTO.getName());
        student.setId(null);
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }



    public CreateStudentResponseDTO getStudent(Long id){


        Student studentResponse = studentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return entityToDto(studentResponse);
    }



    public List<CreateStudentResponseDTO> getAllStudent(){

        List<Student> studentList = studentRepository.findByAndDeletedFalse();

        return studentList.stream()
                .map(x -> this.entityToDto(x))
                .toList();
    }

    public UpdateStudentResponseDTO updateStudent(Long id, UpdateStudentRequestDTO updateStudentRequestDTO){
        Optional<Student> studentFound = studentRepository.findByIdAndDeletedFalse(id);

        if (!studentFound.isPresent()){
            return null;
        }

        Student studentGot = studentFound.get();

        studentGot.setAge(updateStudentRequestDTO.getAge());
        studentGot.setName(updateStudentRequestDTO.getName());
        studentGot.setRollNo(updateStudentRequestDTO.getRollNo());
        studentGot.setSubject(updateStudentRequestDTO.getSubject());
        studentGot.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(studentGot);

        return entityToUpdateDto(studentGot);
    }

    private UpdateStudentResponseDTO entityToUpdateDto(Student student){

        UpdateStudentResponseDTO updateStudentResponseDTO = new UpdateStudentResponseDTO();

        updateStudentResponseDTO.setAge(student.getAge());
        updateStudentResponseDTO.setEmail(student.getEmail());
        updateStudentResponseDTO.setName(student.getName());
        updateStudentResponseDTO.setRollNo(student.getRollNo());
        updateStudentResponseDTO.setSubject(student.getSubject());
        updateStudentResponseDTO.setUpdatedAt(student.getUpdatedAt());

        return updateStudentResponseDTO;
    }

    public Boolean deleteStudent(Long id){


        Optional<Student> result =
                studentRepository.findByIdAndDeletedFalse(id);

        if (result.isEmpty()) {
            return false;
        }

        Student student = result.get();
        student.setDeleted(true);
        studentRepository.save(student);
        return true;
    }



}