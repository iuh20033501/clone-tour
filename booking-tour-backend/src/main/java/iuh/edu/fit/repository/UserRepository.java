package iuh.edu.fit.repository;

import iuh.edu.fit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
//    Optional<User> là kiểu trả về khi tìm kiếm một đối tượng User từ cơ sở dữ liệu.
    Optional<User> findByEmail(String email);
<<<<<<< HEAD

    List<User> findByPhoneContaining(String phone);
=======
    Optional<User> findByPhone(String phone);

>>>>>>> 40ac19bfee75334bfbe9e4267162e4cb42d949e3
}
