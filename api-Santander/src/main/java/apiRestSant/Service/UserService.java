package apiRestSant.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import apiRestSant.Exception.BadRequestException;
import apiRestSant.Exception.UserAlreadyExistException;
import apiRestSant.Model.UserModel;
import apiRestSant.ModelDTO.UserDTO;
import apiRestSant.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final ModelMapper userMapper;//INJETAR MODELMAPPER DIRETO DO "MODELMAPPER" EM VEZ DE CHAMAR A CLASSE USERMAPPER

    public Page<UserModel> findAll(Pageable pageable){
    	return repository.findAll(pageable);
    }
    
    public List<UserModel> findByEmail(String email){
    	return repository.findByEmail(email);
    }
    
    public UserModel create(UserDTO user) throws Exception {
        List<UserModel> userExist = repository.findByEmail(user.getEmail());
        if (!userExist.isEmpty()) {
        	throw new UserAlreadyExistException("Usuário já cadastrado: " + user.getName() + " " + user.getEmail());
        }
       UserModel model = userMapper.map(user, UserModel.class);
        
       return repository.save(model);
    }
    
    public UserModel finfByIdOrThrowBadRequest(Long id) {
    	if (repository.existsById(id)) {
    		return repository.findById(id).
        			orElseThrow(() -> new BadRequestException("Id não encontrado"));
		}else {
			throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
		}
 
    }
    
    public void deleteById(Long id) {
    	if (repository.existsById(id)) {
    		repository.deleteById(id);
		}else {
			throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
		}
    	
    }
}
