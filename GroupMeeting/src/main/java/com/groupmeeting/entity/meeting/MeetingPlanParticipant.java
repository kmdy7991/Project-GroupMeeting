package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting_plan_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlanParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_plan_participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private MeetingPlan meetingPlan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
