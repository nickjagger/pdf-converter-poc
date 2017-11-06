package poc.converter;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface PdfConvertor {
	InputStream convertToPdf(MultipartFile file);
}
