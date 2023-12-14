package com.moudra.HonzikovaMoudra.Repository;

import com.moudra.HonzikovaMoudra.Model.Motto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MottoRepository extends JpaRepository<Motto, Long> {

     void save(String motto);

}
