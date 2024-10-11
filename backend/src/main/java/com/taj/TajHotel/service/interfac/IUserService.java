package com.taj.TajHotel.service.interfac;

import com.taj.TajHotel.dto.LoginRequest;
import com.taj.TajHotel.dto.Response;
import com.taj.TajHotel.entity.User;

public interface IUserService {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
