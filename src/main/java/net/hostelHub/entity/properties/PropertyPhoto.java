package net.hostelHub.entity.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hostelHub.utils.School;

@Getter
@Setter
@Entity
@Table(name = "property_photos")
@AllArgsConstructor
@NoArgsConstructor
public class PropertyPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_name")
    private String propertyName;

    private School schoolName;

    @Column(name = "photo_url")
    private String photoUrl;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    @JsonIgnore
    private HostelProperty hostelProperty;

}
