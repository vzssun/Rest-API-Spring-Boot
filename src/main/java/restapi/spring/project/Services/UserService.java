package restapi.spring.project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restapi.spring.project.Dto.request.UserRequest;
import restapi.spring.project.Dto.response.UserResponse;
import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(UserRequest request) {
        UserModel user = new UserModel();
        user.setUsername(request.getUsername());
        user.setCpf(request.getCpf());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        UserModel savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public List<UserResponse> findAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse findUserById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setUsername(request.getUsername());
        user.setCpf(request.getCpf());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        UserModel updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }
}