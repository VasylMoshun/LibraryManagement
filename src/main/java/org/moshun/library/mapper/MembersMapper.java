package org.moshun.library.mapper;

import org.mapstruct.Mapper;
import org.moshun.library.config.MapperConfig;
import org.moshun.library.dto.MemberRequestDto;
import org.moshun.library.dto.MemberResponseDto;
import org.moshun.library.model.Members;

@Mapper(config = MapperConfig.class)
public interface MembersMapper {
    MemberResponseDto toDto(Members members);

    Members toModel(MemberRequestDto requestDto);
}
