package net.hostelHub.entity.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "room_types")
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_in_a_room")
    private Integer numberInARoom;

    @Column(nullable = false, name = "hostel_name")
    private String hostelName;

    @Column(nullable = false, name = "school_name")
    private String schoolName;

    @Column(nullable = false, name = "unique_code")
    private String uniqueCode;

    @Column(nullable = false, name = "price_per_bed")
    private Double pricePerBed;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private LocalDateTime modifiedAt;

}
