package com.company.db;

import java.sql.ResultSet;

/**
 * Interface for implementation of mapping database result set rows to entities.
 */
public interface EntityMapper<T> {
    T mapRow(ResultSet rs);
}