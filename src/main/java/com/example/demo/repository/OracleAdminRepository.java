package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OracleAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public OracleAdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void compileSchema() {

        String sql = """
                BEGIN
                    DBMS_UTILITY.COMPILE_SCHEMA(
                        schema => USER,
                        compile_all => TRUE
                    );
                END;
                """;

        jdbcTemplate.execute(sql);
    }
}