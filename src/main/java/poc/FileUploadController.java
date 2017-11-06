package poc;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Throwables;

import poc.storage.ConversionFileNotFoundException;
import poc.storage.ConversionService;

@Controller
public class FileUploadController {

	@Autowired
	private final ConversionService conversionService;

	@Autowired
	public FileUploadController(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@PostMapping("/convert")
	public void handleConversion(@RequestParam("file") MultipartFile file, HttpServletResponse response) {

		// get your file as InputStream
		final InputStream is = conversionService.convert(file);

		// copy it to response's OutputStream
		try {
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimEx(RuntimeException ex, Model model) {
		model.addAttribute("ex", ex);
		model.addAttribute("stacktrace", Throwables.getStackTraceAsString(ex));
		return "error";
	}

	@ExceptionHandler(ConversionFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(ConversionFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		return "uploadForm";
	}

}
