package com.tnr.cityguide.storage;
import com.tnr.cityguide.config.AppProperties;
import java.io.*;import java.nio.file.*;import java.util.*;
import org.springframework.stereotype.Service;import org.springframework.web.multipart.MultipartFile;import org.springframework.web.server.ResponseStatusException;import org.springframework.http.HttpStatus;
@Service
public class LocalFileStorageService implements StorageService {
 private final Path root;
 public LocalFileStorageService(AppProperties p){root=Path.of(p.storage().localDir()).toAbsolutePath().normalize();}
 public String store(String folder,MultipartFile file,long maxBytes){
  try{
   if(file==null||file.isEmpty()||file.getSize()>maxBytes)throw invalid(maxBytes);
   byte[] bytes=file.getBytes();String ext=extension(bytes);if(ext==null)throw invalid(maxBytes);
   String safe=folder.replaceAll("[^a-zA-Z0-9/_-]","");Path dir=root.resolve(safe).normalize();if(!dir.startsWith(root))throw invalid(maxBytes);
   Files.createDirectories(dir);String name=UUID.randomUUID()+"."+ext;Path target=dir.resolve(name).normalize();if(!target.startsWith(root))throw invalid(maxBytes);
   Files.write(target,bytes,StandardOpenOption.CREATE_NEW);return "/uploads/"+safe+"/"+name;
  }catch(IOException e){throw new IllegalStateException("Could not store upload",e);}
 }
 public void delete(String path){if(path==null||!path.startsWith("/uploads/"))return;try{Path target=root.resolve(path.substring("/uploads/".length())).normalize();if(target.startsWith(root))Files.deleteIfExists(target);}catch(IOException e){throw new IllegalStateException("Could not delete upload",e);}}
 private String extension(byte[] b){if(b.length>=3&&(b[0]&255)==255&&(b[1]&255)==216&&(b[2]&255)==255)return "jpg";if(b.length>=8&&(b[0]&255)==137&&b[1]==80&&b[2]==78&&b[3]==71&&b[4]==13&&b[5]==10&&b[6]==26&&b[7]==10)return "png";if(b.length>=12&&b[0]=='R'&&b[1]=='I'&&b[2]=='F'&&b[3]=='F'&&b[8]=='W'&&b[9]=='E'&&b[10]=='B'&&b[11]=='P')return "webp";return null;}
 private ResponseStatusException invalid(long max){return new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please choose a JPEG, PNG or WebP image (max "+(max/1024/1024)+" MB).");}
}
