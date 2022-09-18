package dev.t1dmlgus.member.application;

public interface MemberService {

    MemberInfo.MemberToken join(MemberCommand.JoinUser memberCommand);
}
