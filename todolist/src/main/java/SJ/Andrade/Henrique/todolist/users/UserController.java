
package SJ.Andrade.Henrique.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModels userModels){
       var user =  this.userRepository.findByUsername(userModels.getUsername());
       if(user != null){
           System.out.println("User has created");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("is already created");
       }
        //passwordHashred = BCrypt.withDefault().hashToString(cost:12,userModel.getPassword().toCharArray())
       // userModels.setPassword(passwordHashred)

       var userCreated =  this.userRepository.save(userModels);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}