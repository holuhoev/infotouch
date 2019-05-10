package ru.hse.infotouch.domain.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.models.QRoom;
import ru.hse.infotouch.domain.models.Room;
import ru.hse.infotouch.domain.repo.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoomService {

    private final RoomRepository repository;
    private final QRoom qRoom = QRoom.room;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll(Integer buildingId) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (buildingId != null) {
            whereClause.and(qRoom.buildingId.eq(buildingId));
        }

        return StreamSupport
                .stream(this.repository.findAll(whereClause).spliterator(), false)
                .collect(Collectors.toList());
    }

    public Room getOneById(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Аудитории с id \"%d\" не существует", id)));
    }
}
