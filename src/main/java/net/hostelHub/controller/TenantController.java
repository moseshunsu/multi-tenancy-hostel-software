package net.hostelHub.controller;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.tenant.HostelPropertyRequest;
import net.hostelHub.dto.tenant.PropertyPhotoRequest;
import net.hostelHub.entity.tenant.HostelProperty;
import net.hostelHub.service.tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tenants")
public class TenantController {

    @Autowired
    TenantService tenantService;

    @PostMapping("/properties")
    public ResponseEntity<Response> registerProperty(@RequestBody HostelPropertyRequest hostelPropertyRequest) {
        return tenantService.registerProperty(hostelPropertyRequest);
    }

    @PostMapping("/properties/photos")
    public ResponseEntity<Response> addPhoto(@RequestBody PropertyPhotoRequest propertyPhotoRequest) {
        return tenantService.addPhoto(propertyPhotoRequest);
    }

    @GetMapping("/properties")
    public ResponseEntity<List<HostelProperty>> viewAllProperties(@RequestParam String uniqueCode) {
        return tenantService.viewAllProperties(uniqueCode);
    }

    @GetMapping("/properties/property")
    public ResponseEntity<HostelProperty> viewSpecificProperty(@RequestBody HostelPropertyRequest hostelPropertyRequest) {
        return tenantService.viewSpecificProperty(hostelPropertyRequest);
    }

}
