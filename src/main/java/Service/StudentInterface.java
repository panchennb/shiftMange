package Service;

import model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentInterface extends JpaRepository<Student,Long> {
    List<Student> findByCourseId(Long courseId, Pageable pageable);
    List<Student> findByShiftInfoId(String id);
}
