package com.hyo.hyoapp.hyoapp.user;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hyo.hyoapp.hyoapp.DataNotFoundException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String nick, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setNick(nick);;
        user.setPassword(passwordEncoder.encode(password));
        user.setCreate_at(LocalDateTime.now());
        // 역할에 따라 division 값 설정
        if ("ADMIN".equals(username)) {
            user.setDivision(1L); // 어드민
        } else {
            user.setDivision(0L); // 일반 회원
        }
        this.userRepository.save(user);

        return user;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("siteuser not found");
        }
    
    }

}
