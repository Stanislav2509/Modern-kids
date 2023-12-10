package com.app.ModernKids.service.impl;

import com.app.ModernKids.model.dto.QueryBindingModel;
import com.app.ModernKids.model.dto.QueryDTO;
import com.app.ModernKids.model.entity.Order;
import com.app.ModernKids.model.entity.Query;
import com.app.ModernKids.model.entity.Status;
import com.app.ModernKids.model.enums.StatusName;
import com.app.ModernKids.repo.QueryRepository;
import com.app.ModernKids.repo.StatusRepository;
import com.app.ModernKids.service.QueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements QueryService {
    private final QueryRepository queryRepository;
    private final StatusRepository statusRepository;

    public QueryServiceImpl(QueryRepository queryRepository, StatusRepository statusRepository) {
        this.queryRepository = queryRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public void saveQuery(QueryBindingModel queryBindingModel) {
        Status status = statusRepository.getByName(StatusName.NEW.getDisplayValue());
        Query query = new Query();
        query.setFullName(queryBindingModel.getFullName());
        query.setEmail(queryBindingModel.getEmail());
        query.setPhoneNumber(queryBindingModel.getPhoneNumber());
        query.setMessage(queryBindingModel.getMessage());
        query.setStatus(status);

        queryRepository.save(query);
    }

    @Override
    public List<QueryDTO> getByStatus(String displayValue) {
        Status status = statusRepository.getByName(StatusName.NEW.getDisplayValue());
        List<Query> queries =  queryRepository.findAllByStatus(status);
        List<QueryDTO> queryDTOS = new ArrayList<>();

        for (Query query : queries) {
            QueryDTO queryDTO = new QueryDTO();
            queryDTO.setId(query.getId());
            queryDTO.setFullName(query.getFullName());
            queryDTO.setEmail(query.getEmail());
            queryDTO.setPhoneNumber(query.getPhoneNumber());
            queryDTO.setMessage(query.getMessage());

            queryDTOS.add(queryDTO);
        }
        return queryDTOS;
    }

    @Override
    public void updateStatus(Long id, String statusName) {
        Status status = statusRepository.getByName(statusName);
        Optional<Query> queryOptional= queryRepository.findById(id);

        if (queryOptional.isPresent()) {
            Query query = queryOptional.get();;
            query.setStatus(status);
            queryRepository.save(query);
        }
    }
}
