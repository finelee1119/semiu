package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Professor;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProfessorRepository {

    private final EntityManager em;

    public Professor findByName(String name){
        return em.createQuery("SELECT p FROM Professor p WHERE p.name = :name", Professor.class).setParameter("name", name).getSingleResult();
    }

    public List<Professor> findAll() {
            return em.createQuery("SELECT p FROM Professor p", Professor.class)
                    .getResultList();
    }
}
