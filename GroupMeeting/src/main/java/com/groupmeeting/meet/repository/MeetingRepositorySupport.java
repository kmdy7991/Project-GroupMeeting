package com.groupmeeting.meet.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.groupmeeting.dto.meeting.GetMeetingListDto;
import com.groupmeeting.dto.meeting.GetMeetingMemberDto;
import com.groupmeeting.entity.meeting.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MeetingRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<GetMeetingListDto> findByDeletedFalseAndUserId(Long userId) {
        QMeeting m1 = QMeeting.meeting;
        QMeetingMember m2 = QMeetingMember.meetingMember;
        QMeetingPlan meetingPlan = QMeetingPlan.meetingPlan;
        QMeetingPlanParticipant meetingPlanParticipant = QMeetingPlanParticipant.meetingPlanParticipant;

        var result = queryFactory.select(Projections.constructor(
                        GetMeetingListDto.class,
                        m1.id,
                        m1.name,
                        m1.creator.id,
                        m1.creator.nickname,
                        m1.mainImageName,
                        Projections.list(Projections.constructor(
                                        GetMeetingMemberDto.class,
                                        m2.id, m2.user.id, m2.user.nickname, m2.createdAt
                                )
                        ),
                        m1.createdAt,
                        JPAExpressions.select(meetingPlan.startAt.max())
                                .from(meetingPlan)
                                .join(meetingPlanParticipant)
                                .on(meetingPlan.id.eq(meetingPlanParticipant.meetingPlan.id)
                                        .and(meetingPlanParticipant.user.id.eq(userId)))
                                .where(meetingPlan.meeting.id.eq(m1.id))
                ))
                .from(m1)
                .join(m2).on(m1.id.eq(m2.joinedMeeting.id))
                .where(m2.user.id.eq(userId)
                        .and(m1.deleted.isFalse()))
                .orderBy(
                        m1.createdAt.desc()
                )
                .fetch();

        result.forEach(el -> el.setImageUrl(String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                "meeting-sideproject",
                "ap-northeast-2",
                el.getImageUrl()
        )));
        return result;
    }

    public Meeting findByIdOrInviteId(Long meetingId, UUID inviteId) {
        var meeting = QMeeting.meeting;
        var meetingInvite = QMeetingInvite.meetingInvite;
        if (meetingId == null && inviteId == null) {
            return null;
        }
        BooleanExpression whereCondition = null;
        if (meetingId != null) {
            whereCondition = (meeting.id.eq(meetingId));
        }
        if (inviteId != null) {
            whereCondition = whereCondition == null ? meetingInvite.id.eq(inviteId)
                    : whereCondition.and(meetingInvite.id.eq(inviteId));
        }
        return queryFactory.selectFrom(meeting)
                .join(meeting.members)
                .fetchJoin()
                .innerJoin(meeting.invites, meetingInvite)
                .where(whereCondition)
                .orderBy(
                        meeting.createdAt.desc()
                )
                .fetchOne();
    }

    public List<MeetingPlan> findPlansByParticipantUserId(
            Long userId,
            Integer page,
            YearMonth yearMonth,
            Boolean closed
    ) {
        var numInPage = 5;
        var offset = 5 * (page - 1);
        var meetingPlan = QMeetingPlan.meetingPlan;
        var meetingPlanParticipant = QMeetingPlanParticipant.meetingPlanParticipant;
        BooleanExpression yearMonthFilter = null;
        BooleanExpression activeFilter = null;

        if (yearMonth != null) {
            LocalDateTime yearMonthStart = yearMonth.atDay(1).atTime(0, 0, 0);
            LocalDateTime yearMonthEnd = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            yearMonthFilter = meetingPlan.startAt.between(yearMonthStart, yearMonthEnd);
        }

        if (closed != null) {
            var now = LocalDateTime.now();
            activeFilter = closed ? meetingPlan.startAt.before(now) : meetingPlan.startAt.after(now);
        }


        return queryFactory.selectFrom(meetingPlan)
                .innerJoin(meetingPlan.participants, meetingPlanParticipant)
                .fetchJoin()
                .where(meetingPlan.id
                        .in(queryFactory.select(meetingPlan.id)
                                .from(meetingPlan)
                                .innerJoin(
                                        meetingPlan.participants,
                                        meetingPlanParticipant
                                )
                                .where(meetingPlanParticipant.user.id.eq(userId))
                                .fetch()
                        )
                        .and(activeFilter)
                        .and(yearMonthFilter)
                )
                .orderBy(meetingPlan.startAt.asc())
                .limit(numInPage)
                .offset(offset)
                .fetch();
    }

    public List<Meeting> findWhereNoPlansFound() {
        QMeeting meeting = QMeeting.meeting;
        QMeetingPlan meetingPlan = QMeetingPlan.meetingPlan;

        var startAt = LocalDateTime.now().minusHours(1);
        var endAt = startAt.plusMinutes(10);

        return queryFactory
                .selectFrom(meeting)
                .where(JPAExpressions
                        .selectOne()
                        .from(meetingPlan)
                        .where(meetingPlan.meeting.id.eq(meeting.id))
                        .notExists()
                        .and(meeting.createdAt.between(startAt, endAt)))
                .fetch();
    }

    public List<MeetingPlan> findPlansNeedsWeatherToBeUpdated() {
        QMeetingPlan mp = QMeetingPlan.meetingPlan;
        var now = LocalDateTime.now();
        return queryFactory
                .selectFrom(mp)
                .where(mp.startAt.before(now.plusDays(5))
                        .and(mp.weatherUpdatedAt.isNull()
                                .or(mp.weatherUpdatedAt.before(now.minusDays(1))))
                )
                .orderBy(
                        new CaseBuilder()
                                .when(mp.weatherUpdatedAt.isNull())
                                .then(1)
                                .otherwise(2)
                                .asc()
                )
                .limit(60)
                .fetch();
    }

    public MeetingPlan findLatestPlanByMeetingIdAndClosed(Long meetingId, boolean closed) {
        QMeetingPlan mp = QMeetingPlan.meetingPlan;
        var now = LocalDateTime.now();
        var condition = closed ? mp.endAt.before(now) : mp.endAt.isNull().and(mp.endAt.after(now));
        return queryFactory
                .selectFrom(mp)
                .where(mp.meeting.id.eq(meetingId).and(condition))
                .orderBy(mp.startAt.desc())
                .fetchFirst();
    }

}
