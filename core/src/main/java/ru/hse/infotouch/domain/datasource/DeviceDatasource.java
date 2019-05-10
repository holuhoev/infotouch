package ru.hse.infotouch.domain.datasource;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.hse.infotouch.domain.dto.UserDeviceDTO;
import ru.hse.infotouch.domain.models.admin.Device;
import ru.hse.infotouch.domain.models.admin.QDevice;
import ru.hse.infotouch.domain.models.admin.relations.QUser2Device;
import ru.hse.infotouch.util.Strings;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DeviceDatasource {

    @Value("${entities.page-size.default}")
    private int pageSize;

    private final QDevice qDevice = QDevice.device;
    private final QUser2Device qUser2Device = QUser2Device.user2Device;

    private final EntityManager entityManager;

    public DeviceDatasource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Device> findAll(String searchString, int page) {
        BooleanBuilder whereClause = new BooleanBuilder();

        if (StringUtils.isNotEmpty(searchString)) {
            String validated = Strings.removeRedundantSpace(searchString);

            whereClause.and(
                    qDevice.title.containsIgnoreCase(validated)
                            .or(qDevice.description.containsIgnoreCase(validated))
            );
        }

        JPAQuery<Device> query = new JPAQuery<>(entityManager);

        return query.from(qDevice)
                .where(whereClause)
                .offset(pageSize * page)
                .limit(pageSize)
                .fetch();
    }

    public List<UserDeviceDTO> findAllByUserId(int userId) {

        List<Tuple> deviceAndRight = new JPAQuery<>(entityManager).select(qUser2Device.accessRight, qDevice)
                .from(qUser2Device)
                .where(qUser2Device.id.userId.eq(userId))
                .leftJoin(qDevice).on(qDevice.id.eq(qUser2Device.id.deviceId))
                .fetch();

        return deviceAndRight.stream()
                .filter(tuple -> Objects.nonNull(tuple.get(qDevice)))
                .map(tuple -> UserDeviceDTO.createOf(tuple.get(qDevice), tuple.get(qUser2Device.accessRight)))
                .collect(Collectors.toList());
    }
}
