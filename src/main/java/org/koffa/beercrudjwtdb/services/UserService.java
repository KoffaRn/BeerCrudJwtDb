package org.koffa.beercrudjwtdb.services;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.koffa.beercrudjwtdb.models.User;
import org.koffa.beercrudjwtdb.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void deleteById(Long id) throws NoResultException {
        if(userRepository.existsById(id)) userRepository.deleteById(id);
        else throw new NoResultException("No user found for id " + id);
    }
    public User changeUser(User user) throws NoResultException, IllegalArgumentException {
        if(!userRepository.existsById(user.getId())) throw new NoResultException("No user found for id " + user.getId());
        return userRepository.save(user);
    }
}