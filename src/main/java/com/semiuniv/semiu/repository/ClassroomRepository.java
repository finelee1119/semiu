package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Classroom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ClassroomRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Integer> findAllIds() {
        return em.createQuery("SELECT c.id FROM Classroom c", Integer.class)
                .getResultList();
    }

}
