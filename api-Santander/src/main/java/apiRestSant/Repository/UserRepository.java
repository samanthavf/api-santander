package apiRestSant.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apiRestSant.Model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	List<UserModel> findByEmail(String email);
}
