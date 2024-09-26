package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting_plan_comment_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlanCommentReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_plan_comment_report_id")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "original_content", length = 500)
    private String originalContent;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private MeetingPlanComment comment;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private User subject;
}
