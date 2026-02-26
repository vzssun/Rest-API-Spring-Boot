package restapi.spring.project.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;

import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Services.UserService;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping
    //@PreAuthorize("hasAnyAuthority('admin:read', 'management:read')")
  public Page<UserModel> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    return userService.getPaginatedUsers(page,size);
        }
  
}