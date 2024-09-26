package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meeting_plan_review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlanReview extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_plan_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private MeetingPlanParticipant participant;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MeetingPlan meetingPlan;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "review", fetch = FetchType.EAGER)
    private List<MeetingReviewImage> images;
}
