package net.hostelHub.service;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.tenant.HostelPropertyRequest;
import net.hostelHub.entity.User;
import net.hostelHub.entity.tenant.HostelProperty;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.tenant.HostelPropertyRepository;
import net.hostelHub.repository.tenant.PropertyPhotoRepository;
import net.hostelHub.service.tenant.TenantServiceImpl;
import net.hostelHub.utils.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTests {

    @Mock
    private HostelPropertyRepository hostelPropertyRepository;

    @Mock
    private PropertyPhotoRepository propertyPhotoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    TenantServiceImpl tenantService;

    // JUnit test for register property method
    @DisplayName("JUnit test for register property method")
    @Test
    public void givenHostelPropertyRequest_whenRegisterProperty_thenReturnSavedHostelPropertyObject() {
        // Given - Precondition or setup
        String generatedCode = ResponseUtils.generateClientCode(ResponseUtils.LENGTH_OF_TENANT_CODE);

        // Create a new user for the test
        User newUser = User.builder()
                .id(1L)
                .uniqueCode(generatedCode)
                .name("Moses")
                .username("moses")
                .email("moses@gmail.com")
                .phoneNumber("1234567890")
                .password("12345")
                .role(Role.MANAGER)
                .build();

        // Create a HostelPropertyRequest to register a hostel property
        HostelPropertyRequest hostelPropertyRequest = HostelPropertyRequest.builder()
                .uniqueCode(newUser.getUniqueCode())
                .hostelName("Hostel 1")
                .schoolName(School.LAGOS_STATE_UNIVERSITY.name())
                .type(String.valueOf(Type.UNISEX))
                .state(String.valueOf(State.LAGOS))
                .address("Iya Ona Iba")
                .totalRooms(10)
                .contactEmail("gmail.com")
                .contactPhone("12345")
                .build();

        // Create a HostelProperty that will be returned when saving
        HostelProperty hostelProperty = HostelProperty.builder()
                .id(1L)
                .uniqueCode(hostelPropertyRequest.getUniqueCode())
                .hostelName(hostelPropertyRequest.getHostelName())
                .schoolName(School.valueOf(hostelPropertyRequest.getSchoolName()))
                .type(Type.valueOf(hostelPropertyRequest.getType()))
                .state(State.valueOf(hostelPropertyRequest.getState()))
                .address(hostelPropertyRequest.getAddress())
                .totalRooms(hostelPropertyRequest.getTotalRooms())
                .contactEmail(hostelPropertyRequest.getContactEmail())
                .contactPhone(hostelPropertyRequest.getContactPhone())
                .photos(new ArrayList<>())
                .build();

        // Mock the userRepository to return the newUser when findAll() is called
        given(userRepository.findAll()).willReturn(Collections.singletonList(newUser));

        // Mock the hostelPropertyRepository to return hostelProperty when saving
        given(hostelPropertyRepository.save(any(HostelProperty.class))).willReturn(hostelProperty);

        // When - Perform the action to be tested
        ResponseEntity<Response> savedHostelProperty = tenantService.registerProperty(hostelPropertyRequest);
        System.out.println("hostelPropertyRequest = " + hostelPropertyRequest);
        System.out.println("savedHostelProperty = " + savedHostelProperty);

        // Then - Verify the output
        assertThat(savedHostelProperty).isNotNull();
    }

}
