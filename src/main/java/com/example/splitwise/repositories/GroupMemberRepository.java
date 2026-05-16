package com.example.splitwise.repositories;

import com.example.splitwise.models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {

    void deleteByGroupId(int groupId);

    Optional<GroupMember> findByGroupIdAndMemberId(int groupId, int memberToBeAddedUserId);
    @Query("""
    SELECT gm
    FROM GroupMember gm
    JOIN FETCH gm.member
    WHERE gm.group.id=:groupId
    """)
    List<GroupMember> findAllByGroupId(int groupId);
}
