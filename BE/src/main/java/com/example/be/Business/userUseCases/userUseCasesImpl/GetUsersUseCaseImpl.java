package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.userUseCases.GetUsersUseCase;
import com.example.be.Domain.User.GetAllUsersRequest;
import com.example.be.Domain.User.GetAllUsersResponse;
import com.example.be.Domain.User.User;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private UserRepository userRepository;

    @Override
    public GetAllUsersResponse getUsers(final GetAllUsersRequest request) {
        List<UserEntity> results;
//        if (StringUtils.hasText(request.getCountryCode())) {
//            results = customerRepository.findByCountryCode(request.getCountryCode());
//        } else {
            results = userRepository.findAll();
//        }

        List<User> usersDTO = results.stream()
                .map(UserConverter::convert)
                .collect(Collectors.toList());

        final GetAllUsersResponse response = new GetAllUsersResponse();
        response.setUsers(usersDTO);

        return response;
    }
}
