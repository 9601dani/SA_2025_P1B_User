package com.danimo.user.user.application.usecases.listallemployes;

import com.danimo.user.common.application.annotations.UseCase;
import com.danimo.user.user.application.inputports.ListingAllEmployesInputPort;
import com.danimo.user.user.application.outputports.persistence.FindingAllEmployesOutputPort;
import com.danimo.user.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UseCase
public class ListAllEmployesUseCase implements ListingAllEmployesInputPort {

    private final FindingAllEmployesOutputPort findingAllEmployesOutputPort;

    @Autowired
    public  ListAllEmployesUseCase(FindingAllEmployesOutputPort findingAllEmployesOutputPort) {
        this.findingAllEmployesOutputPort = findingAllEmployesOutputPort;
    }

    @Override
    @Transactional
    public List<User> listAllEmployes() {
        return findingAllEmployesOutputPort.findAllEmployes();
    }
}
