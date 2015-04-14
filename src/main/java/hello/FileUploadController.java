package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
            	/**/
            
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "mrithapril",
                        "api_key", "957439151948874",
                        "api_secret", "SjHFnpHphmkoqbL4e-chefMUN7s"));
                
            	File convFile = new File(file.getOriginalFilename());
                convFile.createNewFile(); 
                FileOutputStream fos = new FileOutputStream(convFile); 
                fos.write(file.getBytes());
                fos.close(); 
                File cloudinaryFile = convFile;
                Map uploadResult = cloudinary.uploader().upload(cloudinaryFile, ObjectUtils.emptyMap());    
                System.out.println(uploadResult.get("url"));

            	/**/
                
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

}