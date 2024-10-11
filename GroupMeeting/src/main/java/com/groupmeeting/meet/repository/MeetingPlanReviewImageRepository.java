package com.groupmeeting.meet.repository;

import com.groupmeeting.entity.meeting.MeetingPlanReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingPlanReviewImageRepository extends JpaRepository<MeetingPlanReviewImage, Long> {
    MeetingPlanReviewImage findByReviewId(Long reviewId);
}
