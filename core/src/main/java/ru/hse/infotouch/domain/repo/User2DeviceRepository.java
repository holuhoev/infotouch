package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.infotouch.domain.models.admin.relations.User2Device;

public interface User2DeviceRepository extends JpaRepository<User2Device, Integer> {
}
