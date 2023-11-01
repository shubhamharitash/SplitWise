package service;

import repository.UserRepository;

public class InMemoryService {
    public InMemoryService() {
        UserRepository userRepository=new UserRepository();
    }
}
