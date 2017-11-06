package poc.storage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import poc.converter.ExcelConverter;
import poc.converter.WordConverter;

@Service
public class ConversionServiceImpl implements ConversionService {

	private final WordConverter wordConverter;
	private final ExcelConverter excelConverter;

	@Autowired
	public ConversionServiceImpl(WordConverter wordConverter, ExcelConverter excelConverter) {
		this.wordConverter = wordConverter;
		this.excelConverter = excelConverter;
	}

	@Override
	public InputStream convert(MultipartFile file) {
		final String filename = StringUtils.cleanPath(file.getOriginalFilename());

		if (file.isEmpty())
			throw new ConversionException("Failed to convert empty file " + filename);
		if (filename.contains(".."))
			throw new ConversionException("Cannot convert file with relative path outside current directory " + filename);

		try (InputStream pdf = convertToPdf(file)) {
			return pdf;
		} catch (final IOException e) {
			throw new ConversionException("Failed to convert file " + filename, e);
		}
	}

	private InputStream convertToPdf(MultipartFile file) {

		final String fileType = getFileType(file);
		switch (fileType) {
		case "application/msword":
		case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
			return wordConverter.convertToPdf(file);
		case "application/vnd.ms-excel":
		case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
			return excelConverter.convertToPdf(file);
		default:
			throw new RuntimeException(
					"Unsupported file type [" + fileType + "] for supplied file [" + file.getOriginalFilename() + "]");
		}

	}

	private String getFileType(MultipartFile file) {

		try {
			final TikaConfig config = TikaConfig.getDefaultConfig();
			final Detector detector = config.getDetector();

			final TikaInputStream stream = TikaInputStream.get(file.getBytes());

			final Metadata metadata = new Metadata();
			metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());

			// MediaType mediaType = detector.detect(stream, metadata);
			return detector.detect(stream, metadata).toString();
		} catch (final Throwable t) {
			throw new RuntimeException(t);
		}

	}
}
