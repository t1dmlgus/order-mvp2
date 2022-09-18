package dev.t1dmlgus.member.application;


import dev.t1dmlgus.member.domain.Member;
import dev.t1dmlgus.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public MemberInfo.MemberToken join(MemberCommand.JoinUser memberCommand) {

        Member member = memberCommand.toMember();
        Member save = memberRepository.save(member);
        return MemberInfo.MemberToken.newInstance(save);
    }
}
