package com.example.be.Business.userUseCases;

import com.example.be.Domain.User.GetAllUsersRequest;
import com.example.be.Domain.User.GetAllUsersResponse;

public interface GetUsersUseCase {
    GetAllUsersResponse getUsers(GetAllUsersRequest request);
}
