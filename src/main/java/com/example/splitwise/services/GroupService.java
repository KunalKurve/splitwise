package com.example.splitwise.services;

import com.example.splitwise.models.Group;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GroupService {
    public void deleteGroup(int groupId, int adminId) {
    }

    public Group createGroup(String groupName, String description, int userId) {

        Group group = new Group();
        group.setCreatedAt(new Date());
        group.setGroupName(groupName);
        group.setDescription(description);

        return group;
    }
}
