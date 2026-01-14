package restapi.spring.project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.spring.project.Model.RentalModel;
import restapi.spring.project.Repository.RentalRepository;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalModel> getRentalsByUserId(Long userId) {
        return rentalRepository.findByUserId(userId); // Fetch rentals for a specific user
    }

    public List<RentalModel> getAllRentals() {
        return rentalRepository.findAll(); // Fetch all rentals
    }

    public RentalModel saveRental(RentalModel rental) {
        return rentalRepository.save(rental);
    }

    public Boolean deleteRentalById(Long id) {
        rentalRepository.deleteById(id);
        return true;
    }
}
