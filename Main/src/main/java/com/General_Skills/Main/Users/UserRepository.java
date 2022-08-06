package com.General_Skills.Main.Users;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("Select u FROM User u WHERE u.username = :username")
    User findByUserName(@Param("username")String username);

    @Query("Select u FROM User u ORDER BY u.highScore ASC")
    User[] findByUHighScore();



    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    public void updateUser(@Param("id")int id,@Param("password") String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userData = :userData WHERE u.id = :id")
    public void updatePlayerData(@Param("id")int id,@Param("userData") int userData);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.highScore = :highScore, u.highScoreName = :highScoreName WHERE u.id = :id")
    public void updateHighScore(@Param("id")int id,@Param("highScore") String highScore,@Param("highScoreName") String highScoreName);



}
