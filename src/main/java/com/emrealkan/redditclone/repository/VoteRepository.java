package com.emrealkan.redditclone.repository;

import com.emrealkan.redditclone.model.Post;
import com.emrealkan.redditclone.model.User;
import com.emrealkan.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}