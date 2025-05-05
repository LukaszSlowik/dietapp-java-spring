package org.nutrihealthplan.dietapp.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.nutrihealthplan.dietapp.model.User;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class) //only Mockito without spring
@ExtendWith(SpringExtension.class) // only when spring context
@Import(CustomAuditorAware.class) // only when spring context - for testing this class
public class CustomAuditorAwareTest {
    //@Mock //only Mockito without spring
    @MockitoBean // only when spring context
            UserRepository userRepository;

    //@InjectMocks //only Mockito without spring
    @Autowired // only when spring context
            CustomAuditorAware auditorAware;

    @Test
    @WithMockUser
    void shouldReturnEmptyWhenAuthenticationIsNull(){
        //given
        //SecurityContextHolder.clearContext();
        //when
        Optional<User> result = auditorAware.getCurrentAuditor();
        //then
        assertThat(result).isEmpty();
    }
    @Test
    @WithMockUser(username = "test@Example.com")
    void shouldReturnUserWhenAuthenticated(){
        //given
        String email = "test@Example.com";
        User user = new User();
        user.setEmail(email);
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(authentication.getName()).thenReturn(email);
//        SecurityContext context = mock(SecurityContext.class);
//        when(context.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(context);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        Optional<User> result = auditorAware.getCurrentAuditor();
        //then

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(u-> assertThat(u.getEmail()).isEqualTo(email));

    }
}

