package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.MeetingPlanComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingPlanCommentRepository extends JpaRepository<MeetingPlanComment, Long> {
    Optional<MeetingPlanComment> findByIdAndDeletedAtIsNull(Long commentId);
}
