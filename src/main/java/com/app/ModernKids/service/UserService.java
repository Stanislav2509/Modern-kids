package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.UserRegisterBindingModel;

public interface UserService {
    boolean register(UserRegisterBindingModel userRegisterBindingModel);
}
