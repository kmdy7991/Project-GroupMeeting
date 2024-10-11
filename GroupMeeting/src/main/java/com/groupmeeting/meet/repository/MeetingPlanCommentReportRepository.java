package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.MeetingPlanCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingPlanCommentReportRepository extends JpaRepository<MeetingPlanCommentReport, Long> {
    MeetingPlanCommentReport findByReporterIdAndCommentId(Long reporterId, Long commentId);
}
