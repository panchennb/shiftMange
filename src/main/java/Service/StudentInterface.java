package Service;

import model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentInterface extends JpaRepository<Student,Long> {
    List findByCourseId(Long courseId);
}
