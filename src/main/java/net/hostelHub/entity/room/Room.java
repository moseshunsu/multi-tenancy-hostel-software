package net.hostelHub.entity.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hostelHub.utils.RoomStatus;
import net.hostelHub.utils.Sex;

@Setter
@Getter
@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private String roomNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    @JsonIgnore
    private RoomType roomType;

    @Column(nullable = false, name = "unique_code")
    private String uniqueCode;

    @Column(nullable = false, name = "hostel_name")
    private String hostelName;

    @Column(nullable = false, name = "school_name")
    private String schoolName;

    @Column(nullable = false, name = "price_per_bed")
    private Double pricePerBed;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "bed_available")
    private Integer bedAvailable; // This indicates the number of beds available and increases based on booking

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

}
