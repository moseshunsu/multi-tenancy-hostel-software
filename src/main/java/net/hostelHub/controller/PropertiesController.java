package net.hostelHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Property Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow hostel managers create and manage their " +
                "hostel properties"
)
public class PropertiesController {
    private final HostelPropertyService hostelPropertyService;

    @Operation(
            summary = "This endpoint allows property owners register their properties",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Property Exists",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Response> registerProperty(@RequestBody @Valid HostelPropertyRequest hostelPropertyRequest) {
        return hostelPropertyService.registerProperty(hostelPropertyRequest);
    }

    @Operation(
            summary = "This endpoint allows property owners add url of their property images",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Hostel not found",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping("/photos")
    public ResponseEntity<Response> addPhoto(@RequestBody PropertyPhotoRequest propertyPhotoRequest) {
        return hostelPropertyService.addPhoto(propertyPhotoRequest);
    }

    @Operation(
            summary = "This endpoint allows property owners view a list of all their properties",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No hostel found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<HostelProperty>> viewAllProperties(@RequestParam String uniqueCode) {
        return hostelPropertyService.viewAllProperties(uniqueCode);
    }

    @Operation(
            summary = "This endpoint allows property owners view a specific property. ** Pass in unique code, hostel " +
                    "& school name ",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No hostel found",
                            responseCode = "400"
                    )
            }
    )
    @GetMapping("/property")
    public ResponseEntity<HostelProperty> viewSpecificProperty(@RequestBody HostelPropertyRequest hostelPropertyRequest) {
        return hostelPropertyService.viewSpecificProperty(hostelPropertyRequest);
    }

    @Operation(
            summary = "This endpoint returns a list of hostels in a particular school",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No content",
                            responseCode = "204"
                    )
            }
    )
    @GetMapping("/hostels")
    public ResponseEntity<Set<HostelProperty>> fetchAvailableHostels(@RequestParam String schoolName) {
        return hostelPropertyService.fetchAvailableHostels(schoolName);
    }

}
