package com.app.ModernKids.service;

import com.app.ModernKids.model.dto.QueryBindingModel;
import com.app.ModernKids.model.dto.QueryDTO;

import java.util.List;

public interface QueryService {
    void saveQuery(QueryBindingModel queryBindingModel);

    List<QueryDTO> getByStatus(String displayValue);

    void updateStatus(Long id, String status);
}
