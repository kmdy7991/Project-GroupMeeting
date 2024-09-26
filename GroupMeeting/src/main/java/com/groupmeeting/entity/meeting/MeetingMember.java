package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meeting_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_id")
    private Meeting joinedMeeting;
}
