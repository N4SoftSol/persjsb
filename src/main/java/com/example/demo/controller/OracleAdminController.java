package com.example.demo.controller;

import com.example.demo.dto.JwtUserInfo;
import com.example.demo.service.AuditService;
import com.example.demo.service.OracleAdminService;
import com.example.demo.service.PersonService;
import com.example.demo.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/oracle")
@Slf4j
public class OracleAdminController {

    private final OracleAdminService oracleAdminService;
    private final PersonService personService;
    private final AuditService auditService;


    public OracleAdminController(OracleAdminService oracleAdminService, PersonService personService, AuditService auditService) {
        this.oracleAdminService = oracleAdminService;
        this.personService = personService;
        this.auditService = auditService;
    }

    @PostMapping("/compile-schema")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Map<String, String>> compileSchema(Authentication authentication) {
        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);

        oracleAdminService.compileSchema();

        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                "COMPILE_SCHEMA",
                "ORACLE",
                "SUCCESS",
                "DBMS_UTILITY.COMPILE_SCHEMA");
        return ResponseEntity.ok(
                Map.of(
                        "status", "SUCCESS",
                        "message", "Oracle schema compilation completed successfully."
                )
        );
    }
}