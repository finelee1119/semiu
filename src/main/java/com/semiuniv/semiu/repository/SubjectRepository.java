package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Subject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class SubjectRepository {

    private final EntityManager em;


    public List<Subject> findAll() {
        return em.createQuery("select s from Subject s", Subject.class)
                .getResultList();
    }
    public void insertSubject(Subject subject) {
        em.merge(subject);
    }

    public Subject findById(int updateId){
        return em.find(Subject.class, updateId);
    }

    public void deleteSubject(Subject subject) {
        em.remove(subject);
    }

}
