package net.hostelHub.service.properties;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.properties.HostelPropertyRequest;
import net.hostelHub.dto.properties.PropertyPhotoRequest;
import net.hostelHub.entity.properties.HostelProperty;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface HostelPropertyService {
    ResponseEntity<Response> registerProperty(HostelPropertyRequest hostelPropertyRequest);
    ResponseEntity<Response> addPhoto(PropertyPhotoRequest propertyPhotoRequest);
    ResponseEntity<List<HostelProperty>> viewAllProperties(String uniqueCode);
    ResponseEntity<HostelProperty> viewSpecificProperty(HostelPropertyRequest hostelPropertyRequest);
    ResponseEntity<Set<HostelProperty>> fetchAvailableHostels(String schoolName);

}
