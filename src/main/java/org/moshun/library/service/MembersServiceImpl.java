package org.moshun.library.service;

import lombok.RequiredArgsConstructor;
import org.moshun.library.dto.MemberRequestDto;
import org.moshun.library.dto.MemberResponseDto;
import org.moshun.library.mapper.MembersMapper;
import org.moshun.library.model.Members;
import org.moshun.library.repository.MembersRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembersServiceImpl implements MembersService {
    private final MembersRepository membersRepository;
    private final MembersMapper membersMapper;

    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        Members members = new Members();
        members.setName(requestDto.getName());
        membersRepository.save(members);
        return membersMapper.toDto(members);
    }

    public void deleteMemberById(Long id) {
        Optional<Members> membersById = Optional.of(membersRepository.findById(id)).orElseThrow(
                () -> new NoSuchElementException("No member with id=" + id));
        if (membersById.isPresent()) {
            Members members = membersById.get();
            if (members.getBorrowedBookList().size() == 0) {
                membersRepository.delete(members);
            }
        }
    }

    public void updateMemberById(Long id, MemberRequestDto requestDto) {
        membersRepository.findById(id).ifPresent(
                membersRepository.save(members -> members.setName(requestDto.getName())));
    }

    public MemberResponseDto getMemberById(Long id) {
        return membersMapper.toDto(membersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No member with id=" + id)));
    }

    public List<MemberResponseDto> getAllMembers(Pageable pageable) {
        return membersRepository.findAll().stream()
                .map(membersMapper::toDto)
                .toList();
    }
}
