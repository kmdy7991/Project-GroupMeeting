package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
}
