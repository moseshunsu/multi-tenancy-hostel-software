package net.hostelHub.repository.properties;

import net.hostelHub.entity.properties.HostelProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelPropertyRepository extends JpaRepository<HostelProperty, Long> {
}
