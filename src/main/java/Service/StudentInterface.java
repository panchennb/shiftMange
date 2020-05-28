package Service;

import model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInterface extends JpaRepository<Student,Long> {

}
