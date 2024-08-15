package org.moshun.library.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.moshun.library.dto.MemberRequestDto;
import org.moshun.library.dto.MemberResponseDto;
import org.moshun.library.service.MembersService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MembersController {
    private final MembersService membersService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "Get all member")
    public List<MemberResponseDto> getAllMembers(Pageable pageable) {
        return membersService.getAllMembers(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "get meber by id")
    public MemberResponseDto getMemberById(@PathVariable Long id) {
        return membersService.getMemberById(id);
    }

    @PostMapping
    @Tag(name = "Create member")
    public MemberResponseDto createMember(@RequestBody @Valid MemberRequestDto requestDto) {
        return membersService.createMember(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "update member")
    public void updateMember(
            @RequestBody @Valid MemberRequestDto requestDto, @PathVariable Long id) {
        membersService.updateMemberById(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "delete member")
    public void deleteBook(@PathVariable Long id) {
        membersService.deleteMemberById(id);
    }
}
