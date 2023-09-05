package net.hostelHub.entity.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hostelHub.utils.RoomStatus;
import net.hostelHub.utils.Sex;

@Setter @Getter @Entity @Table(name = "rooms")
@AllArgsConstructor @NoArgsConstructor
public class Room {

    @Id
    private String roomNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    @JsonIgnore
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private int bedAvailable; // This indicates the number of beds available and increases based on booking

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

}
