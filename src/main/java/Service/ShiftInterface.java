package Service;

import model.ShiftInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftInterface extends JpaRepository<ShiftInfo,Long> {

}
