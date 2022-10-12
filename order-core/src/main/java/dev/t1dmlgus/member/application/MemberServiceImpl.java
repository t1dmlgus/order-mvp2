package dev.t1dmlgus.member.application;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.DuplicateException;
import dev.t1dmlgus.common.error.exception.InvalidException;
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
        existEmail(member);
        Member save = memberRepository.save(member);
        return MemberInfo.MemberToken.newInstance(save);
    }

    private void existEmail(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateException(ErrorType.DUPLICATED_EMAIL);
        }
    }

    @Override
    public MemberInfo.MemberToken login(MemberCommand.login memberCommand) {

        Member member = validateLogin(memberCommand);
        return MemberInfo.MemberToken.newInstance(member);
    }

    private Member validateLogin(MemberCommand.login memberCommand) {

        String email = memberCommand.getEmail();
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(memberCommand.getPassword()))
                .orElseThrow(() -> new InvalidException(ErrorType.INVALID_LOGIN));
    }
}
