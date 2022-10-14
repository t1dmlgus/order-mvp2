package dev.t1dmlgus.member.application;

public interface MemberService {
    /**
     * 회원가입 서비스
     *
     * @param memberCommand DTO to Join
     * @return memberToken
     */
    MemberInfo.MemberToken join(MemberCommand.JoinUser memberCommand);



    /**
     * 로그인 서비스
     *
     * @param memberCommand DTO to login
     * @return memberToken
     */
    MemberInfo.MemberToken login(MemberCommand.login memberCommand);
}
