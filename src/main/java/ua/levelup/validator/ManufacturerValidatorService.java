package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

/**
 *
 */
@Service("manufacturerValidatorService")
public class ManufacturerValidatorService extends AbstractValidatorService<ManufacturerCreateDto> { }