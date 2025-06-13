package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.VerificationDto;
import com.essay.TieuLuan_BE.entity.Verification;

public class VerificationDtoMapper {
    public static VerificationDto toVerificationDto(Verification verification) {
        VerificationDto verificationDto = new VerificationDto();
        verificationDto.setStatus(verification.isStatus());
        verificationDto.setStartedAt(verification.getStartedAt());
        verificationDto.setEndsAt(verification.getEndsAt());
        verificationDto.setPlanType(verification.getPlanType());
        return  verificationDto;
    }
}
