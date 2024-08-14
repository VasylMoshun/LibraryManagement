package org.moshun.library.service;

import org.moshun.library.dto.MemberRequestDto;
import org.moshun.library.dto.MemberResponseDto;
import org.moshun.library.model.Members;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface MembersService {
    public MemberResponseDto createMember(MemberRequestDto requestDto);

    public void deleteMemberById(Long id);

    public void updateMemberById(Long id, MemberRequestDto requestDto);

    public MemberResponseDto getMemberById(Long id);

    public List<MemberResponseDto> getAllMembers(Pageable pageable);
}
