package com.tnr.cityguide.storage;
import org.springframework.web.multipart.MultipartFile;
public interface StorageService {
 StoredImage store(String folder,MultipartFile file,long maxBytes);
 StoredImage migrateLegacy(String legacyUrl,String folder,long maxBytes);
 void delete(String publicId);
 record StoredImage(String url,String publicId){}
}