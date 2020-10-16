package org.meteorite.com.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author EX_052100260
 * @title: DemoApplicationConfiguration
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-25 10:57
 */
@Slf4j
@Configuration
public class DemoApplicationConfiguration {

    @Bean
    public UserDetailsService testUserDetailService() {

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        String[][] usersGroupsAndRoles = {
                {"huolongguo", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"}
        };

        for (String[] user : usersGroupsAndRoles) {

            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
            inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
                    authoritiesStrings.stream().map(e->
                    new SimpleGrantedAuthority(e)
            ).collect(Collectors.toList())));
        }

        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
