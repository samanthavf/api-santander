package apiRestSant.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apiRestSant.Model.UserModel;
import apiRestSant.ModelDTO.UserDTO;
import apiRestSant.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("Teste")
public class UserController {
	private final UserService serviceList;
	
	@GetMapping("/pages")
		public ResponseEntity<Page<UserModel>> findAll(Pageable pageable){
		return ResponseEntity.ok(serviceList.findAll(pageable));
	}
	
	@PostMapping("/create")
	public ResponseEntity<UserModel> create(@RequestBody @Valid UserDTO dto) throws Exception{
		return new ResponseEntity<>(serviceList.create(dto), HttpStatus.CREATED);
	}
	
	@GetMapping(path = "search/email-{email}")
	public ResponseEntity<List<UserModel>> findByName(@PathVariable String email){
		return ResponseEntity.ok(serviceList.findByEmail(email));
	}
	
	@GetMapping(path = "search/id-{id}")
	public ResponseEntity<UserModel> finfById(@PathVariable Long id){
		return ResponseEntity.ok(serviceList.finfByIdOrThrowBadRequest(id));
	}

	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		serviceList.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}


