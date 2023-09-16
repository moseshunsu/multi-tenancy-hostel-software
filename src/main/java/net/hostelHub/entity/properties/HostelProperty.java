package net.hostelHub.entity.properties;

import jakarta.persistence.*;
import lombok.*;
import net.hostelHub.utils.School;
import net.hostelHub.utils.State;
import net.hostelHub.utils.Type;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "hostel_properties")
public class HostelProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uniqueCode;

    @Column(nullable = false)
    private String hostelName;

    @Enumerated(EnumType.STRING)
    private School schoolName;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    private String address;

    private String description;

    @Column(name = "total_rooms")
    private int totalRooms;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Builder.Default
    @OneToMany(mappedBy = "hostelProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyPhoto> photos = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
