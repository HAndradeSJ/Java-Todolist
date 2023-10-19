package  SJ.Andrade.Henrique.todolist.users;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModels,UUID> {
 UserModels findByUsername(String username);
}