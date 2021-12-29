package com.project.backend.repository;

import com.project.backend.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select h from History h where h.user.id = ?1")
    List<History> findAllByUserId(Long userId);

    @Query("select h from History h where h.location.id = ?1")
    List<History> findAllByLocationId(Long locationId);
}
