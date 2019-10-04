package br.com.dvs.resource;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dvs.domain.Operations;
import br.com.dvs.service.ArduinoService;

@RestController
public class ArduinoResource {

	private static final Logger logger = Logger.getLogger(ArduinoService.class);

	public ArduinoResource() {
	}

	@RequestMapping(value = "/sensor", method = RequestMethod.GET)
	public ResponseEntity<String> listar() {
		ArduinoService service = ArduinoService.getSingleton();
		String data = service.getLastData();

		SimpleDateFormat simpleTime = new SimpleDateFormat("H:mm:ss");
		String dateFormated = simpleTime.format(new Date());

		String jsonFormatted = "{ result: " + data + ", date: " + dateFormated + "}";
		return new ResponseEntity<String>(jsonFormatted, HttpStatus.OK);
	}

	@RequestMapping(value = "/actuator/{operation}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeStateWithPath(@PathVariable(value = "operation") String operation) {
		String jsonFormatted = execute(operation);
		return new ResponseEntity<>(jsonFormatted, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/actuator", method = RequestMethod.PUT)
	public ResponseEntity<?> changeStateWithBody(@RequestBody String operation) {
		String jsonFormatted = execute(operation);
		return new ResponseEntity<>(jsonFormatted, HttpStatus.OK);
	}

	private String execute(String operation) {
		Operations op = Operations.valueOf(operation.toUpperCase());
		logger.info("Executing " + op.toString() );

		ArduinoService service = ArduinoService.getSingleton();
		service.execute(op);

		String jsonFormatted = "{ result: Status changed to " + op.toString() + "}";
		logger.info(jsonFormatted);
		return jsonFormatted;
	}
}