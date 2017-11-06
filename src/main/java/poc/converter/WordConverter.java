package poc.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;

@Service
public class WordConverter implements PdfConvertor {

	@Override
	public InputStream convertToPdf(MultipartFile file) {
		try {
			// convert file to Aspose doc
			final Document doc = new Document(file.getInputStream());

			// convert to PDF and save results to stream
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.save(out, new PdfSaveOptions());

			return new ByteArrayInputStream(out.toByteArray());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

}
