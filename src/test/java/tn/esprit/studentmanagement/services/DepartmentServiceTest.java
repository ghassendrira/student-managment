package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department dept1;
    private Department dept2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dept1 = new Department();
        dept1.setName("IT");

        dept2 = new Department();
        dept2.setName("HR");
    }


    @Test
    void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(dept1, dept2));

        List<Department> departments = departmentService.getAllDepartments();

        assertEquals(2, departments.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById_Found() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(dept1));

        Department result = departmentService.getDepartmentById(1L);

        assertEquals("IT", result.getName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(3L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                departmentService.getDepartmentById(3L)
        );
        assertTrue(exception.getMessage().contains("Department not found"));
    }

    @Test
    void testSaveDepartment() {
        when(departmentRepository.save(dept1)).thenReturn(dept1);

        Department result = departmentService.saveDepartment(dept1);

        assertEquals(dept1, result);
        verify(departmentRepository, times(1)).save(dept1);
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentRepository).deleteById(1L);

        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }
}
