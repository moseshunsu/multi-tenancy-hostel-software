package net.hostelHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.properties.HostelPropertyRequest;
import net.hostelHub.dto.properties.PropertyPhotoRequest;
import net.hostelHub.entity.properties.HostelProperty;
import net.hostelHub.service.properties.HostelPropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
public class PropertiesController {
    private final HostelPropertyService hostelPropertyService;

    @PostMapping
    public ResponseEntity<Response> registerProperty(@RequestBody @Valid HostelPropertyRequest hostelPropertyRequest) {
        return hostelPropertyService.registerProperty(hostelPropertyRequest);
    }

    @PostMapping("/photos")
    public ResponseEntity<Response> addPhoto(@RequestBody PropertyPhotoRequest propertyPhotoRequest) {
        return hostelPropertyService.addPhoto(propertyPhotoRequest);
    }

    @GetMapping
    public ResponseEntity<List<HostelProperty>> viewAllProperties(@RequestParam String uniqueCode) {
        return hostelPropertyService.viewAllProperties(uniqueCode);
    }

    @GetMapping("/property")
    public ResponseEntity<HostelProperty> viewSpecificProperty(@RequestBody HostelPropertyRequest hostelPropertyRequest) {
        return hostelPropertyService.viewSpecificProperty(hostelPropertyRequest);
    }

    @GetMapping("/hostels")
    public ResponseEntity<Set<HostelProperty>> fetchAvailableHostels(@RequestParam String schoolName) {
        return hostelPropertyService.fetchAvailableHostels(schoolName);
    }

}
