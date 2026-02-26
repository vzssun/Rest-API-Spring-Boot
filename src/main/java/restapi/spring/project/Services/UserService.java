package restapi.spring.project.Services;

import restapi.spring.project.Model.UserModel;
import restapi.spring.project.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
@Autowired
    private UserRepository userRepository;

    public Page<UserModel> getPaginatedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}