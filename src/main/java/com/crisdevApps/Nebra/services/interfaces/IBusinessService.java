package com.crisdevApps.Nebra.services.interfaces;


import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetBusinessDTO;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBusinessService {

    List<GetBusinessDTO> GetNearBusiness(double latitude, double longitude);

    void CreateBusiness(CreateBusinessDTO createBusinessDTO, UUID userId);

    void UpdateBusiness(UpdateBusinessDTO updateBusinessDTO, UUID userId);

    void DeleteBusiness(UUID businessId, UUID userId) ;

    void StoreBusinessImages(List<MultipartFile> photos, UUID businessId, UUID userId);

    List<GetBusinessDTO> SearchBusiness(String search, int page);

    List<GetBusinessDTO> GetUserArchivedBusiness(int page, UUID userId);

    List<GetBusinessDTO> FilterBusinessByCategory(BusinessCategory businessCategory, int page);

    List<GetBusinessDTO> GetUserBusiness(UUID userId, int page);

    void ArchiveBusiness(UUID businessId, UUID userId);

    void RepublishBusiness(UUID businessId, UUID userId);

    Business GetValidBusiness(UUID businessId);

    void AddBusinessToUserFavorites(UUID businessId, UUID userId);

    void RemoveBusinessFromUserFavorites(UUID businessId, UUID userId);

    List<GetBusinessDTO> GetUserFavoriteBusiness(UUID userId);

}
