package net.hostelHub.repository.room;

import net.hostelHub.entity.room.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}