package com.example.demo.service;

import com.example.demo.exception.UknownFileFormatException;
import com.example.demo.exception.UploadFileIsEmptyException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalStorageService localStorageService;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User getByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        if (usernameOrEmail.contains("@")) {
            return userRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));
        } else {
            return userRepository.findByUsername(usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));
        }
    }

    public void updateAvatar(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).
                orElseThrow();

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new UknownFileFormatException();
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new UploadFileIsEmptyException();
        }

        String filename = "image_avatar_" + user.getId() + "_" + System.currentTimeMillis() + extension;
        String fileUrl = localStorageService.uploadFile(file, filename);

        user.setAvatarUrl("http://localhost:8080/uploads/products/" + filename);
        userRepository.save(user);

    }
}
