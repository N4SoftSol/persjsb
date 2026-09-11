package com.example.demo.service;

import com.example.demo.repository.OracleAdminRepository;
import org.springframework.stereotype.Service;

@Service
public class OracleAdminService {

    private final OracleAdminRepository oracleAdminRepository;

    public OracleAdminService(OracleAdminRepository oracleAdminRepository) {
        this.oracleAdminRepository = oracleAdminRepository;
    }

    public void compileSchema() {
        oracleAdminRepository.compileSchema();
    }
}