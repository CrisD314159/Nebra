package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailService {

    void SendEmail(EmailDTO emailDTO, String template, boolean verification);
}
