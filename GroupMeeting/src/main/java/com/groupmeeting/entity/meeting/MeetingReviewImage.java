package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting_review_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingReviewImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_review_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private MeetingPlanReview review;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MeetingPlan plan;

    @Column(name = "review_image")
    private String reviewImage;
}
