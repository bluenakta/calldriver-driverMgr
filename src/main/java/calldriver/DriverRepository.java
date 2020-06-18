package calldriver;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DriverRepository extends PagingAndSortingRepository<Driver, Long> {

    List<Driver> findByStatus(String status);

    Driver findByCallId(Long callId);

}