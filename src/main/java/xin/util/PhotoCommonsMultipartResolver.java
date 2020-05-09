package xin.util;


import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by snowman on 2017/10/26.
 * 头像限制大小
 */
public class PhotoCommonsMultipartResolver extends CommonsMultipartResolver {

    protected MultipartParsingResult parseRequest(HttpServletRequest request)throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = this.prepareFileUpload(encoding,request);
        try {
            List fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);

        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
                    ex);
        } catch (FileUploadException ex) {
            throw new MultipartException(
                    "Could not parse multipart servlet request", ex);
        }

    }

    protected FileUpload prepareFileUpload(String encoding,HttpServletRequest request) {
        FileUpload fileUpload = getFileUpload();
        FileUpload actualFileUpload = fileUpload;
        // Use new temporary FileUpload instance if the request specifies
        // its own encoding that does not match the default encoding.
        if (encoding != null && encoding.equals(fileUpload.getHeaderEncoding())) {
            actualFileUpload = newFileUpload(getFileItemFactory());
            actualFileUpload.setHeaderEncoding(encoding);
            boolean isAddProduct = request.getRequestURI().contains("UserStudentMapper/modifyPicture");
            if(isAddProduct){
                actualFileUpload.setSizeMax(512 * 1024 * 10);//头像重新设置文件限制5M
            }else{
                actualFileUpload.setSizeMax(fileUpload.getSizeMax());
            }
        }
        return actualFileUpload;
    }
}

