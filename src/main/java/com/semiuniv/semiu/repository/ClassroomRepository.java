package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    @Query(value = "SELECT classroom_id FROM Classroom ", nativeQuery = true)
    List<Integer> findAllIds();
}
