package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_plan_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlanComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_plan_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private MeetingPlanParticipant participant;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MeetingPlan meetingPlan;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
