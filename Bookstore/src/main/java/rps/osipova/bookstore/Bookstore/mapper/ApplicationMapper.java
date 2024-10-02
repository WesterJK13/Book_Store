package rps.osipova.bookstore.Bookstore.mapper;

import org.mapstruct.Mapper;
import rps.osipova.bookstore.Bookstore.dto.ApplicationDTO;
import rps.osipova.bookstore.Bookstore.models.Application;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application transform(ApplicationDTO applicationDTO);

    ApplicationDTO transform(Application application);

    List<Application> transformFromDTOList(List<ApplicationDTO> applicationDTOList);

    List<ApplicationDTO> transformFromList(List<Application> applicationList);

}
