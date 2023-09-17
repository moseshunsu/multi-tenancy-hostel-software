package net.hostelHub.entity.booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hostelHub.utils.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "bookings")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "unique_booking_number")
    @NaturalId
    private String uniqueBookingNumber;

    @Column(nullable = false, name = "hostel_name")
    private String hostelName;

    @Column(nullable = false, name = "unique_occupant_code")
    private String uniqueOccupantCode; //For the student

    @Column(nullable = false, name = "occupant_email")
    private String occupantEmail;

    @Column(nullable = false, name = "unique_manager_code")
    private String uniqueManagerCode; //For the hostel owner

    @Column(nullable = false, name = "hostel_contact_email")
    private String hostelContactEMail;

    @Column(nullable = false)
    private String schoolName;

    @Column(name = "academic_year")
    private String academicYear;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(updatable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
