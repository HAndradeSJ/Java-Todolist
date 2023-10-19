package  SJ.Andrade.Henrique.todolist.task;

import SJ.Andrade.Henrique.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public  class TaskController {
    @Autowired
    private  ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        taskModel.setIdUser((UUID) request.getAttribute("IdUser"));

        var currentDate = LocalDateTime.now();
        if(currentDate.isBefore(taskModel.getStartAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("arrival date is less than the task date stard");
        }

        if(currentDate.isBefore(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("arrival date is less than the task date final");
        }
        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("start date is less than the task date final");

        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }


    @GetMapping("/get")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        return this.taskRepository.findByIdUser((UUID)idUser);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel,HttpServletRequest request,@PathVariable UUID id){

        var task = this.taskRepository.findById(id).orElse(null);
        var idUser = request.getAttribute("idUser");

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("task not found !");
        }

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("is task not create by this user ");
        }

        Utils.copyNull(taskModel,task);

        var save = this.taskRepository.save(taskModel);
        return  ResponseEntity.status(HttpStatus.OK).body(save);

    }
}