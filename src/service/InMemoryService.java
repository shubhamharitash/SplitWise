package service;

import contants.ShowConstants;
import repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Paths;

public class InMemoryService {
    public InMemoryService() {
        UserRepository userRepository=new UserRepository();
        try {
            Files.deleteIfExists(Paths.get(ShowConstants.outputFilePath));
            Files.createFile(Paths.get(ShowConstants.outputFilePath));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
