package net.network.server.resource;

import lombok.RequiredArgsConstructor;
import net.network.server.model.Response;
import net.network.server.model.Server;
import net.network.server.service.implementation.ServerServiceImp;
import org.netbeans.lib.cvsclient.response.CreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.callback.ConfirmationCallback;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static net.network.server.enumeration.Status.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.valueOf;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImp serverService;

    //Gets list of all IP Addresses
    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        ResponseEntity<Response> ok;
        ok = ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        //below should be OK.value()) according to training
                        .statusCode(ConfirmationCallback.OK)
                        .build()
        );
        return ok;
    }

    //Get the status of the server Up or Down
    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        ResponseEntity<Response> ok;
        ok = ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        //below should be OK.value()) according to training
                        .statusCode(ConfirmationCallback.OK)
                        .build()
        );
        return ok;
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        ResponseEntity<Response> ok;
        ok = ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serverService.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        //below should be CREATED.value()) according to training
                        .statusCode(ConfirmationCallback.OK)
                        .build()
        );
        return ok;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> pingServer(@PathVariable("id") Long id) {
        ResponseEntity<Response> ok;
        ok = ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("server", serverService.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        //below should be OK.value()) according to training
                        .statusCode(ConfirmationCallback.OK)
                        .build()
        );
        return ok;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        ResponseEntity<Response> ok;
        ok = ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        //below should be OK.value()) according to training
                        .statusCode(ConfirmationCallback.OK)
                        .build()
        );
        return ok;
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException, NoSuchFileException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/ServerIcons/" + fileName));
    }
}