package com.vti.profile.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.vti.profile.entity.UserProfile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    @Query("MATCH (u:user_profile) WHERE u.userId = $userId RETURN u")
    Optional<UserProfile> findByUserId(String userId);
    List<UserProfile> findAllByUsernameLike(String username);
}
