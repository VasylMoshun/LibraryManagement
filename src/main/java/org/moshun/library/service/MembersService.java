package org.moshun.library.service;

import org.moshun.library.dto.MemberRequestDto;
import org.moshun.library.dto.MemberResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MembersService {
    MemberResponseDto createMember(MemberRequestDto requestDto);

    void deleteMemberById(Long id);

    void updateMemberById(Long id, MemberRequestDto requestDto);

    MemberResponseDto getMemberById(Long id);

    List<MemberResponseDto> getAllMembers(Pageable pageable);
}
