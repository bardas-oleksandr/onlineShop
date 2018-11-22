package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

/**
 *
 */
@Service("manufacturerValidatorService")
@Profile("test")
public class ManufacturerValidatorService extends AbstractValidatorService<ManufacturerCreateDto> { }