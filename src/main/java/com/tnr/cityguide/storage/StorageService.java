package com.tnr.cityguide.storage;
import org.springframework.web.multipart.MultipartFile;
public interface StorageService {String store(String folder,MultipartFile file,long maxBytes);void delete(String path);}
