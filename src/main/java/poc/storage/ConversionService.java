package poc.storage;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ConversionService {
	InputStream convert(MultipartFile file);

}
