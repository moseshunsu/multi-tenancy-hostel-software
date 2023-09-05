package net.hostelHub.service.tenant;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.tenant.HostelPropertyRequest;
import net.hostelHub.dto.tenant.PropertyPhotoRequest;
import net.hostelHub.entity.tenant.HostelProperty;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TenantService {
    ResponseEntity<Response> registerProperty(HostelPropertyRequest hostelPropertyRequest);
    ResponseEntity<Response> addPhoto(PropertyPhotoRequest propertyPhotoRequest);
    ResponseEntity<List<HostelProperty>> viewAllProperties(String uniqueCode);
    ResponseEntity<HostelProperty> viewSpecificProperty(HostelPropertyRequest hostelPropertyRequest);
    //Updating property details
}
