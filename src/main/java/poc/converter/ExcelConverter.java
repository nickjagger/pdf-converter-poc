package poc.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;

@Service
public class ExcelConverter implements PdfConvertor {

	@Override
	public InputStream convertToPdf(MultipartFile file) {
		try {

			// convert file to Aspose spreadsheet
			final Workbook wb = new Workbook(file.getInputStream());

			// Specify Pdf Save Options - Stream Provider
			final PdfSaveOptions opts = new PdfSaveOptions();
			opts.setOnePagePerSheet(true);
			// opts.setStreamProvider(new MyStreamProvider());

			// Save the workbook to Pdf
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			wb.save(out, opts);

			return new ByteArrayInputStream(out.toByteArray());

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
