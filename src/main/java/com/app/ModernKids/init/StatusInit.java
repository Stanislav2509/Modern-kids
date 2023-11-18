package com.app.ModernKids.init;

import com.app.ModernKids.model.entity.Status;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.repo.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StatusInit implements CommandLineRunner {
    private final StatusRepository statusRepository;

    public StatusInit(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... args) {
        long countStatuses = statusRepository.count();

        if(countStatuses == 0){
            List<Status> statuses = new ArrayList<>();
            Arrays.stream(StatusName.values()).forEach(statusName -> {
                Status status = new Status();
                status.setName(statusName.getDisplayValue());
                statuses.add(status);
            });
            statusRepository.saveAll(statuses);
        }
    }
}
