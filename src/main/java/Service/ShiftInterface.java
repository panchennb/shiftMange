package Service;

import model.ShiftInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ShiftInterface extends JpaRepository<ShiftInfo,Long> {
    ShiftInfo findByCourseId(Long courseId);
}
