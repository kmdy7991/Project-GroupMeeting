package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.MeetingPlanParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingPlanParticipantRepository extends JpaRepository<MeetingPlanParticipant, Long> {
    Optional<MeetingPlanParticipant> findByUserIdAndMeetingPlanId(Long userId, Long meetingPlanId);
}
