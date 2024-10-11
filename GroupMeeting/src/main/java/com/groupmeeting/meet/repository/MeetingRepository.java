package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByDeletedFalseAndCreatorId(Long creatorId);
}
