package net.hostelHub.service.properties;

import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.properties.HostelPropertyRequest;
import net.hostelHub.dto.properties.PropertyPhotoRequest;
import net.hostelHub.entity.User;
import net.hostelHub.entity.properties.HostelProperty;
import net.hostelHub.entity.properties.PropertyPhoto;
import net.hostelHub.exception.NoSuchElementException;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.properties.HostelPropertyRepository;
import net.hostelHub.repository.properties.PropertyPhotoRepository;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.School;
import net.hostelHub.utils.State;
import net.hostelHub.utils.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private HostelPropertyRepository hostelPropertyRepository;
    @Autowired
    private PropertyPhotoRepository propertyPhotoRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Response> registerProperty(HostelPropertyRequest hostelPropertyRequest) {

        boolean propertyExists = hostelPropertyRepository.findAll().stream()
                .anyMatch(hostel -> hostel.getHostelName().equalsIgnoreCase(hostelPropertyRequest.getHostelName()) &&
                        hostel.getSchoolName().equals(School.valueOf(hostelPropertyRequest.getSchoolName()))
                );

        if (propertyExists) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .responseCode(ResponseUtils.PROPERTY_EXISTS_CODE)
                    .responseMessage(ResponseUtils.PROPERTY_EXISTS_MESSAGE)
                    .data(
                            Data.builder()
                                    .username(hostelPropertyRequest.getHostelName())
                                    .build()
                    )
                    .build()
            );
        }

        User fetchedUser = userRepository.findAll()
                .stream()
                .filter(user -> user.getUniqueCode().equalsIgnoreCase(hostelPropertyRequest.getUniqueCode()))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException(
                                            "User with this code not found: " + hostelPropertyRequest.getUniqueCode()
                ));

        HostelProperty property = new HostelProperty();
        property.setHostelName(hostelPropertyRequest.getHostelName());
        property.setSchoolName(School.valueOf(hostelPropertyRequest.getSchoolName()));
        property.setState(State.valueOf(hostelPropertyRequest.getState()));
        property.setAddress(hostelPropertyRequest.getAddress());
        property.setDescription(hostelPropertyRequest.getDescription());
        property.setTotalRooms(hostelPropertyRequest.getTotalRooms());
        property.setContactEmail(hostelPropertyRequest.getContactEmail());
        property.setContactPhone(hostelPropertyRequest.getContactPhone());
        property.setType(Type.valueOf(hostelPropertyRequest.getType()));
        property.setUniqueCode(fetchedUser.getUniqueCode());

        HostelProperty savedProperty = hostelPropertyRepository.save(property);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.PROPERTY_REGISTER_SUCCESS_CODE)
                        .responseMessage(ResponseUtils.REGISTER_PROPERTY_SUCCESS_MESSAGE)
                        .data(
                                Data.builder()
                                        .uniqueCode(fetchedUser.getUniqueCode())
                                        .username(fetchedUser.getUsername())
                                        .email(fetchedUser.getEmail())
                                        .build()
                        )
                        .build()
        );
    }

    @Override
    public ResponseEntity<Response> addPhoto(PropertyPhotoRequest propertyPhotoRequest) {

        HostelProperty hostelProperty = hostelPropertyRepository.findAll()
                .stream()
                .filter(property -> propertyPhotoRequest.getPropertyName().equalsIgnoreCase(property.getHostelName()) &&
                                    propertyPhotoRequest.getSchoolName().equals(property.getSchoolName()))
                .findFirst()
                .orElseThrow( () -> new NoSuchElementException("No such hostel found; check hostel name and school"));

        PropertyPhoto propertyPhoto = new PropertyPhoto();
        propertyPhoto.setPropertyName(hostelProperty.getHostelName());
        propertyPhoto.setSchoolName(hostelProperty.getSchoolName());
        propertyPhoto.setPhotoUrl(propertyPhotoRequest.getPhotoUrl());
        propertyPhoto.setDescription(propertyPhotoRequest.getDescription());
        propertyPhoto.setHostelProperty(hostelProperty);

        propertyPhotoRepository.save(propertyPhoto);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.PHOTO_UPDATE_MESSAGE)
                        .data(
                                Data.builder()
                                        .uniqueCode(hostelProperty.getUniqueCode())
                                        .build()
                        )
                        .build()
        );
    }

    @Override
    public ResponseEntity<List<HostelProperty>> viewAllProperties(String uniqueCode) {
        List<HostelProperty> hostelProperties = hostelPropertyRepository.findAll().stream().filter(
                hostelProperty -> hostelProperty.getUniqueCode().equalsIgnoreCase(uniqueCode)
        ).toList();

        return !hostelProperties.isEmpty()
                ? ResponseEntity.ok().body(hostelProperties)
                : ResponseEntity.badRequest().body(null);
    }

    @Override
    public ResponseEntity<HostelProperty> viewSpecificProperty(HostelPropertyRequest hostelPropertyRequest) {
        return ResponseEntity.ok().body(
                hostelPropertyRepository.findAll()
                .stream()
                .filter(hostelProperty ->
                            hostelPropertyRequest.getHostelName().equalsIgnoreCase(hostelProperty.getHostelName()) &&
                            hostelPropertyRequest.getUniqueCode().equalsIgnoreCase(hostelProperty.getUniqueCode()) &&
                            School.valueOf(hostelPropertyRequest.getSchoolName()).equals(hostelProperty.getSchoolName())
                )
                .findFirst()
                .orElseThrow( () -> new NoSuchElementException("No such hostel found; check hostel name and school"))
        );
    }

}
