package net.hostelHub.repository.tenant;

import net.hostelHub.entity.tenant.HostelProperty;
import net.hostelHub.utils.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HostelPropertyRepository extends JpaRepository<HostelProperty, Long> {
}
