package com.example.splitwise.repositories;

import com.example.splitwise.models.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Integer> {
    Optional<GroupAdmin> findByGroupIdAndAdminId(int groupId, int userId);

    void deleteByGroupId(int groupId);

    List<GroupAdmin> findAllByGroupId(int groupId);
}
