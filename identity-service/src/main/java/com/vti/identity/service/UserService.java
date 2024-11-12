package com.vti.identity.service;

import java.util.HashSet;
import java.util.List;

import com.vti.identity.dto.request.ApiResponse;
import com.vti.identity.dto.response.UserProfileResponse;
import com.vti.identity.mapper.ProfileMapper;
import com.vti.identity.repository.httpClient.ProfileClient;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vti.identity.constant.PredefinedRole;
import com.vti.identity.dto.request.UserCreationRequest;
import com.vti.identity.dto.request.UserUpdateRequest;
import com.vti.identity.dto.response.UserResponse;
import com.vti.identity.entity.Role;
import com.vti.identity.entity.User;
import com.vti.identity.exception.AppException;
import com.vti.identity.exception.ErrorCode;
import com.vti.identity.mapper.UserMapper;
import com.vti.identity.repository.RoleRepository;
import com.vti.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);
        user = userRepository.save(user);

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        log.info("In method create profile:{}", profileRequest);

        ApiResponse<UserProfileResponse> userProfileResponse = profileClient.createProfile(profileRequest);
        log.info("In method create profile:{}", userProfileResponse);
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setFirstName(userProfileResponse.getResult().getFirstName());
        userResponse.setLastName(userProfileResponse.getResult().getLastName());
        userResponse.setDob(userProfileResponse.getResult().getDob());
        return userResponse;
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse = userMapper.toUserResponse(user);
        ApiResponse<UserProfileResponse> profileResponse = profileClient.getProfileByUserId(user.getId());
        userResponse.setFirstName(profileResponse.getResult().getFirstName());
        userResponse.setLastName(profileResponse.getResult().getLastName());
        userResponse.setDob(profileResponse.getResult().getDob());
        return userResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
        log.info("In method get header:{}", authHeader);

        return userRepository.findAll().stream()
                .map(user -> {
                    UserResponse userResponse = userMapper.toUserResponse(user);
                    log.info("In method get User {}", user);

                    try {
                        ApiResponse<UserProfileResponse> profileResponse = profileClient.getProfileByUserId(user.getId());
                        if (profileResponse != null) {
                            userResponse.setFirstName(profileResponse.getResult().getFirstName());
                            userResponse.setLastName(profileResponse.getResult().getLastName());
                            userResponse.setDob(profileResponse.getResult().getDob());
                        } else {
                            log.warn("Profile response is null for user ID: {}", user.getId());
                        }
                    } catch (Exception e) {
                        log.error("Failed to fetch profile for user ID: {}", user.getId(), e);
                        new AppException(ErrorCode.USER_NOT_EXISTED);
                    }
                    return userResponse;
                })
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
}
