package net.hostelHub.dto.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.hostelHub.utils.School;

@Setter
@Getter
@Builder
public class PropertyPhotoRequest {
    private String propertyName;
    private School schoolName;
    private String photoUrl;
    private String description;
}
