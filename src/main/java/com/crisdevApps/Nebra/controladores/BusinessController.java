package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.UpdateBusinessDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateBusinessDTO;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.security.UserDetailsImpl;
import com.crisdevApps.Nebra.services.interfaces.IBusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {
    private final IBusinessService businessService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> createBusiness(
            @Valid @RequestBody CreateBusinessDTO createBusinessDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        UUID userId = userDetails.getId();
        businessService.CreateBusiness(createBusinessDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "Business Created"));
    }

    @PostMapping("/uploadBusinessPhotos")
    public ResponseEntity<List<String>> uploadBusinessPhotos(
            @RequestParam("files") List<MultipartFile> photos,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        List<String> businessImages = businessService.StoreBusinessImages(photos, userId);
        return ResponseEntity.ok(businessImages);
    }

    @PutMapping()
    public ResponseEntity<ResponseMessage> updateBusiness (
            @Valid @RequestBody UpdateBusinessDTO updateBusinessDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        UUID userId = userDetails.getId();
        businessService.UpdateBusiness(updateBusinessDTO, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business Updated"));
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<ResponseMessage> archiveBusiness (
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        UUID userId = userDetails.getId();
        businessService.ArchiveBusiness(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business Archived"));
    }

    @PutMapping("/republish/{id}")
    public ResponseEntity<ResponseMessage> republishBusiness (
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        businessService.RepublishBusiness(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business republished"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteBusiness(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        businessService.DeleteBusiness(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business Deleted"));
    }


    @GetMapping("/search")
    public ResponseEntity<List<GetBusinessDTO>> searchBusiness(@RequestParam int page, @RequestParam String name){
        List<GetBusinessDTO> businessDTOS = businessService.SearchBusiness(name, page);
        return ResponseEntity.ok(businessDTOS);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<GetBusinessDTO>> getUserArchived(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        List<GetBusinessDTO> businessDTOS =  businessService.GetUserArchivedBusiness(userId);
        return ResponseEntity.ok(businessDTOS);
    }

    @GetMapping("/category")
    public ResponseEntity<List<GetBusinessDTO>> getBusinessByCategory(
            @RequestParam int page,
            @RequestParam BusinessCategory category
    ){
        List<GetBusinessDTO> businessDTOS =  businessService.FilterBusinessByCategory(category, page);
        return ResponseEntity.ok(businessDTOS);
    }

    @GetMapping("/near")
    public ResponseEntity<List<GetBusinessDTO>> getNearBusiness(
            @RequestParam double latitude,
            @RequestParam double longitude
    ){
        List<GetBusinessDTO> businessDTOS =  businessService.GetNearBusiness(latitude, longitude);
        return ResponseEntity.ok(businessDTOS);
    }

    @GetMapping("/user")
    public ResponseEntity<List<GetBusinessDTO>> getUserBusiness(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        List<GetBusinessDTO> businessDTOS =  businessService.GetUserBusiness(userId);
        return ResponseEntity.ok(businessDTOS);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<GetBusinessDTO>> getUserFavoriteBusiness(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        List<GetBusinessDTO> businessDTOS =  businessService.GetUserFavoriteBusiness(userId);
        return ResponseEntity.ok(businessDTOS);
    }

    @PutMapping("/favorites/add/{id}")
    public ResponseEntity<ResponseMessage> addBusinessToUserFavorites(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID id
    ){
        UUID userId = userDetails.getId();
        businessService.AddBusinessToUserFavorites(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business Added to favorites"));
    }

    @PutMapping("/favorites/remove/{id}")
    public ResponseEntity<ResponseMessage> removeBusinessFromUserFavorites(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID id
    ){
        UUID userId = userDetails.getId();
        businessService.RemoveBusinessFromUserFavorites(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Business removed to favorites"));
    }
}
