package com.renjian.fivechase.Repository;

import com.renjian.fivechase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User getByUsernameAndPassword(String username,String password);
}
