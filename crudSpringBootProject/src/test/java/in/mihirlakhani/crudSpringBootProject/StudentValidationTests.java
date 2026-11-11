package in.mihirlakhani.crudSpringBootProject;

import in.mihirlakhani.crudSpringBootProject.controller.StudentController;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentRequestDTO;
import in.mihirlakhani.crudSpringBootProject.dto.CreateStudentResponseDTO;
import in.mihirlakhani.crudSpringBootProject.exception.GlobalExceptionHandler;
import in.mihirlakhani.crudSpringBootProject.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentValidationTests {
    private StudentService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = mock(StudentService.class);
        mvc = MockMvcBuilders.standaloneSetup(new StudentController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void invalidFieldsReturnAnnotationMessagesWithoutCallingService() throws Exception {
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":" ","email":"invalid","age":17}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name[0]").value("Name is required"))
                .andExpect(jsonPath("$.email[0]").value("Email must be a valid email address"))
                .andExpect(jsonPath("$.age[0]").value("Age must be at least 18"));
        verifyNoInteractions(service);
    }

    @Test
    void nameLongerThan150CharactersReturnsLengthMessage() throws Exception {
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + "a".repeat(151)
                                + "\",\"email\":\"student@example.com\",\"age\":18}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name[0]").value("Name must not exceed 150 characters"));
        verifyNoInteractions(service);
    }

    @Test
    void validBoundaryValuesReachService() throws Exception {
        when(service.createStudent(any(CreateStudentRequestDTO.class)))
                .thenReturn(new CreateStudentResponseDTO());
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + "a".repeat(150)
                                + "\",\"email\":\"student@example.com\",\"age\":18}"))
                .andExpect(status().isCreated());
        verify(service).createStudent(any(CreateStudentRequestDTO.class));
    }
}
