package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment e1;
    private Enrollment e2;
    private Student s1;
    private Student s2;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Création des étudiants
        s1 = new Student();
        s1.setFirstName("Alice");
        s1.setLastName("Smith");

        s2 = new Student();
        s2.setFirstName("Bob");
        s2.setLastName("Johnson");

        // Création des enrollments liés aux étudiants
        e1 = new Enrollment();
        e1.setStudent(s1);

        e2 = new Enrollment();
        e2.setStudent(s2);
    }

    @Test
    void testGetAllEnrollments() {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        assertEquals(2, enrollments.size());
        assertEquals("Alice", enrollments.get(0).getStudent().getFirstName());
        assertEquals("Bob", enrollments.get(1).getStudent().getFirstName());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void testGetEnrollmentById_Found() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(e1));

        Enrollment result = enrollmentService.getEnrollmentById(1L);

        assertEquals("Alice", result.getStudent().getFirstName());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEnrollmentById_NotFound() {
        when(enrollmentRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                enrollmentService.getEnrollmentById(3L)
        );
        assertTrue(exception.getMessage().contains("Enrollment not found"));
    }

    @Test
    void testSaveEnrollment() {
        when(enrollmentRepository.save(e1)).thenReturn(e1);

        Enrollment result = enrollmentService.saveEnrollment(e1);

        assertEquals(e1, result);
        verify(enrollmentRepository, times(1)).save(e1);
    }

    @Test
    void testDeleteEnrollment() {
        doNothing().when(enrollmentRepository).deleteById(1L);

        enrollmentService.deleteEnrollment(1L);

        verify(enrollmentRepository, times(1)).deleteById(1L);
    }
}
