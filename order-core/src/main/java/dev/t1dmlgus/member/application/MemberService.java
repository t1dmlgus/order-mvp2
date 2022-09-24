package dev.t1dmlgus.member.application;

public interface MemberService {
    /**
     * 회원가입
     *
     * @param memberCommand param to Join
     * @return memberToken
     */
    MemberInfo.MemberToken join(MemberCommand.JoinUser memberCommand);
}
