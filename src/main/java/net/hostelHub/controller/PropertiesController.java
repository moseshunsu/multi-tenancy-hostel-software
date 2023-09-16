package net.hostelHub.controller;

import jakarta.validation.Valid;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.properties.HostelPropertyRequest;
import net.hostelHub.dto.properties.PropertyPhotoRequest;
import net.hostelHub.entity.properties.HostelProperty;
import net.hostelHub.service.properties.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
public class PropertiesController {

    @Autowired
    TenantService tenantService;

    @PostMapping
    public ResponseEntity<Response> registerProperty(@RequestBody @Valid HostelPropertyRequest hostelPropertyRequest) {
        return tenantService.registerProperty(hostelPropertyRequest);
    }

    @PostMapping("/photos")
    public ResponseEntity<Response> addPhoto(@RequestBody PropertyPhotoRequest propertyPhotoRequest) {
        return tenantService.addPhoto(propertyPhotoRequest);
    }

    @GetMapping
    public ResponseEntity<List<HostelProperty>> viewAllProperties(@RequestParam String uniqueCode) {
        return tenantService.viewAllProperties(uniqueCode);
    }

    @GetMapping("/property")
    public ResponseEntity<HostelProperty> viewSpecificProperty(@RequestBody HostelPropertyRequest hostelPropertyRequest) {
        return tenantService.viewSpecificProperty(hostelPropertyRequest);
    }

}
