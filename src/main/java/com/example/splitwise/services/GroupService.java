package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupAdminRepository groupAdminRepository;

    @Autowired
    public GroupService(
            UserRepository userRepository,
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            GroupAdminRepository groupAdminRepository
    ){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupAdminRepository = groupAdminRepository;
    }

    @Transactional
    public void deleteGroup(int groupId, int userId) throws InvalidGroupException, InvalidUserException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroupIdAndAdminId(group.getId(), user.getId());
        if (groupAdminOptional.isEmpty()){
            throw new UnAuthorizedAccessException("The given user is not an admin of the group");
        }

        for (GroupMember groupMember : group.getMembers()){
            groupMemberRepository.delete(groupMember);
        }
        for (GroupAdmin groupAdmin : group.getAdmins()){
            groupAdminRepository.delete(groupAdmin);
        }
        //or
        //groupAdminRepository.deleteByGroupId(group.getId());
        //groupMemberRepository.deleteByGroupId(group.getId());
        groupRepository.delete(group);

    }

    public Group createGroup(String groupName, String description, int userId) throws InvalidUserException, UnAuthorizedAccessException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));


        Group group = new Group();
        group.setCreatedAt(new Date());
        group.setGroupName(groupName);
        group.setDescription(description);
        group.setCreatedBy(user);
        Group savedGroup = groupRepository.save(group);

        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setAdmin(user);
        groupAdmin.setGroup(savedGroup);
        groupAdmin.setAddedBy(user);
        groupAdminRepository.save(groupAdmin);

        List<GroupMember> members = new ArrayList<>();
        GroupMember groupMember = new GroupMember();
        groupMember.setMember(user);
        groupMember.setAddedBy(user);
        groupMember.setGroup(savedGroup);
        groupMember.setAddedAt(new Date());
        members.add(groupMember);
        savedGroup.setMembers(members);

        return groupRepository.save(savedGroup);
    }

    public GroupMember addMember(int groupId, int adminId ,int memberToBeAddedUserId) throws InvalidGroupException, InvalidUserException, UnAuthorizedAccessException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        GroupAdmin groupAdmin = groupAdminRepository.findByGroupIdAndAdminId(groupId, adminId)
                .orElseThrow(()-> new UnAuthorizedAccessException("The given user is not an admin of the group"));

        User memberToBeAdded = userRepository.findById(memberToBeAddedUserId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findByGroupIdAndMemberId(groupId, memberToBeAddedUserId);
        if(optionalGroupMember.isPresent()){
            throw new InvalidUserException("The user is already a Group member");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setMember(memberToBeAdded);
        groupMember.setGroup(group);
        groupMember.setAddedBy(groupAdmin.getAdmin());
        groupMember.setAddedAt(new Date());
        return groupMemberRepository.save(groupMember);
    }


    public void removeMember(int groupId, int adminId, int memberId) throws InvalidGroupException, InvalidUserException, UnAuthorizedAccessException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        GroupAdmin groupAdmin = groupAdminRepository.findByGroupIdAndAdminId(groupId, adminId)
                .orElseThrow(()-> new UnAuthorizedAccessException("The given user is not an admin of the group"));

        User memberToBeRemoved = userRepository.findById(memberId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        Optional<GroupMember> optionalGroupMember = groupMemberRepository.findByGroupIdAndMemberId(groupId, memberId);
        if(optionalGroupMember.isEmpty()){
            throw new InvalidUserException("The Given user is not a member of the group");
        }

        groupMemberRepository.delete(optionalGroupMember.get());
    }

    public List<User> fetchAllMembers(int groupId, int userId) throws InvalidGroupException, InvalidUserException {

        groupRepository.findById(groupId)
                .orElseThrow(()-> new InvalidGroupException("Group not found"));

        userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException("User not found"));

        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByGroupIdAndMemberId(groupId, userId);

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroupIdAndAdminId(groupId, userId);

        if(groupAdminOptional.isEmpty() && groupMemberOptional.isEmpty()){
            throw new InvalidUserException("The Given user is not a member of the group");
        }

        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(groupId);
        List<GroupAdmin> groupAdmins = groupAdminRepository.findAllByGroupId(groupId);

        HashSet<User> uniqueUsers = new HashSet<>();
        for (GroupMember groupMember : groupMembers){
            uniqueUsers.add(groupMember.getMember());
        }

        for (GroupAdmin groupAdmin : groupAdmins){
            uniqueUsers.add(groupAdmin.getAdmin());
        }

        return uniqueUsers.stream().toList();
    }
}
