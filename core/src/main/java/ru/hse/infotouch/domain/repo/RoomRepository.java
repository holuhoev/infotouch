package ru.hse.infotouch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.hse.infotouch.domain.models.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>, QuerydslPredicateExecutor<Room> {
}
