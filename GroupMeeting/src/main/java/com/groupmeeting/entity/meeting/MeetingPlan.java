package com.groupmeeting.entity.meeting;

import com.groupmeeting.entity.common.BaseTimeEntity;
import com.groupmeeting.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "meeting_plan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingPlan extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_plan_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "lat", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "lot", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "weather_id")
    private Integer weatherId;

    @Column(name = "weather_icon")
    private String weatherIcon;

    @Column(name = "weather_update_at")
    private LocalDateTime weatherUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "metting_id")
    private Meeting meeting;

    @OneToMany(mappedBy = "meetingPlan", cascade = CascadeType.ALL)
    private List<MeetingPlanParticipant> participants;

    @OneToMany(mappedBy = "meetingPlan", cascade = CascadeType.ALL)
    private List<MeetingPlanComment> comments;

    @Transient
    public List<MeetingPlanComment> getActiveComments() {
        return this.comments.stream()
                .filter(comment -> comment.getDeletedAt() == null).toList();
    }
}
