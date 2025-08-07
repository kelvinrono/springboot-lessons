package com.demoapp.students.repositories;

import com.demoapp.students.models.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaspportRepository extends JpaRepository<Passport, Integer> {

}
